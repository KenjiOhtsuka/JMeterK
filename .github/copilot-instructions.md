# JMeterK Copilot Instructions

## Project Overview

JMeterK is a Kotlin DSL library for programmatically constructing JMeter test plans and serializing them to `.jmx` XML files. It mirrors the JMeter element hierarchy in Kotlin with a builder/DSL pattern.

## Build & Test Commands

```bash
# Build all modules
./gradlew build

# Build library only
./gradlew :library:build

# Run library tests
./gradlew :library:test

# Run a single test class
./gradlew :library:test --tests "tools.kenjiotsuka.jmeterk.SomeTest"

# Run sample application
./gradlew :sample:run
```

- Kotlin 2.3.0, JVM toolchain 21
- JUnit Jupiter (JUnit 5) for tests
- Integration test: `TestPlanSerializationTest` compares `buildJmxDocument()` output against `src/test/resources/test.jmx`

## Project Structure

```
JMeterK/
  build.gradle.kts        ← root: shared config (group, version, repositories)
  settings.gradle.kts     ← declares include("library", "sample")
  library/                ← the JMeterK library (publishable artifact)
    build.gradle.kts
    src/main/             ← library source
    src/test/             ← library tests
  sample/                 ← runnable sample project using the library
    build.gradle.kts      ← implementation(project(":library"))
    src/main/             ← sample code
  testfile/               ← reference JMX files
```

The `sample` module depends on `:library` via `implementation(project(":library"))` and demonstrates typical DSL usage. Run it with `./gradlew :sample:run`.

## Architecture

The codebase has two layers:

### 1. `model/` — Domain Model + DSL
- `model/core/JMeterElement.kt` defines the base hierarchy:
  - `JMeterElement` → abstract base
  - `JMeterContainer` → elements that hold children (e.g. `TestPlan`, `ThreadGroup`)
  - `JMeterLeaf` → childless elements
  - `JMeterElementBuilder` / `JMeterContainerBuilder` / `JMeterLeafBuilder` → corresponding builder base classes
- Each element type lives in a sub-package matching its JMeter category:
  - `model/core/` — `TestPlan`, `AnyElement`
  - `model/thread/` — `ThreadGroup`
  - `model/sampler/` — `HttpRequest`
  - `model/assertion/` — `ResponseAssertion`, `Jsr223Assertion`
  - Empty packages exist for future elements: `configelement/`, `listener/`, `nontestelement/`, `postprocessor/`, `preprocessors/`, `testfragment/`, `timer/`

The directory structure and classnames are based on JMeter GUI.
The attributes of the classes are based on inputs of JMeter GUI.

#### Naming conventions: GUI label vs JMX attribute name

Model field names follow the **JMeter GUI label** (e.g. `ignoreStatus`, `cacheCompiledScriptIfAvailable`). The serialization layer in `jmx/` maps these to the JMX XML attribute names (e.g. `Assertion.assume_success`, `cacheKey`). This separation keeps the DSL readable while producing spec-correct JMX output.

Enum values use Kotlin SCREAMING_SNAKE_CASE. Enums that map to specific JMX string values carry a property for the JMX identifier (e.g. `Language.GROOVY` carries `scriptLanguage = "groovy"`).

#### AnyElement

`model/core/AnyElement.kt` provides a generic element for JMX nodes that do not yet have a typed model class. Its purpose is to represent any JMX XML element without data loss, so the structure mirrors JMX XML closely:

| Field | Meaning |
|---|---|
| `tagName` | XML tag name (e.g. `"ThreadGroup"`, `"stringProp"`) |
| `attributes` | XML attributes (e.g. `mapOf("testname" to "My Plan")`) |
| `value` | Text content for leaf nodes (e.g. `"1"` for a `stringProp`) |
| `configNodes` | XML-internal sub-nodes (see _children vs configNodes_ below) |
| `children` | GUI-visible child elements (inherited from `JMeterContainer`) |

### 2. `jmx/` — JMX XML Serialization
- `JmxElement.kt` — `sealed class JmxNode` with three variants:
  - `JmxElement` — an XML element with attributes and children
  - `JmxText` — raw text content inside an element
  - `JmxHashTree` — represents a JMeter `<hashTree>` node (see _hashTree wrapping_ below)
  - Property helper fns: `stringProp`, `boolProp`, `intProp`, `longProp`, `elementProp` (multiple overloads)
- `JmxConverter.kt` — renders a `JmxNode` tree to an XML string with HTML escaping (`JmxNode → String`)
- `JmxWriter.kt` — delegates to `JmxConverter` and writes the result to a `File`
- `JmxDispatch.kt` — three responsibilities:
  1. `fun JMeterElement.toJmxNode()` — single `when` expression routing any element to its `toJmxNode()` extension fn
  2. `fun JMeterElement.toJmxSubtree(): List<JmxNode>` — returns `[element, JmxHashTree(childSubtrees)]`
  3. `fun buildJmxDocument(plan: TestPlan): String` — builds the full XML document string including the `<?xml?>` declaration and `<jmeterTestPlan>` root
- Per-element extension functions (`TestPlanJmx.kt`, `ThreadGroupJmx.kt`, etc.) — convert model objects to `JmxElement` trees

#### Serialization design

Model classes in `model/` are pure data and have no knowledge of any output format. Serialization logic lives in `jmx/` as **extension functions**, one file per element type (e.g. `fun ThreadGroup.toJmxNode(): JmxElement` in `ThreadGroupJmx.kt`). This keeps `model/` free of format-specific code and allows new output formats (YAML, etc.) to be added as separate packages without touching model classes.

The export pipeline is: `TestPlan → buildJmxDocument() → String → File`

#### hashTree wrapping

JMeter's JMX format uses a **flat sibling** structure, not XML nesting, to represent parent–child relationships. Every element is followed by a `<hashTree>` tag that contains its GUI children:

```xml
<TestPlan><!-- config props only --></TestPlan>
<hashTree>
  <ThreadGroup><!-- config props only --></ThreadGroup>
  <hashTree>
    <HTTPSamplerProxy>…</HTTPSamplerProxy>
    <hashTree>…</hashTree>
  </hashTree>
</hashTree>
```

**Consequence**: `toJmxNode()` for any element must return **only the element's own config properties** — never its GUI children. GUI children are placed in the sibling `<hashTree>` by `toJmxSubtree()`. Calling `addAll(children.map { it.toJmxNode() })` inside a `toJmxNode()` implementation is wrong.

#### `enabled` attribute convention

The `enabled` XML attribute is **only emitted when its value is `false`**. When `enabled = true` (the default), the attribute is omitted entirely, matching the JMeter JMX format.

### Reference file
`testfile/test.jmx` is a hand-authored JMX file showing the expected XML output structure. `src/test/resources/test.jmx` is a copy used by the integration test.

## Key Conventions

### Adding a new JMeter element
Every new element requires these four pieces:
1. **Model class** in `model/<category>/` extending `JMeterContainer` or `JMeterLeaf`
2. **Builder class** in the same file extending `JMeterContainerBuilder` or `JMeterLeafBuilder`. Also add a DSL method to each **parent Builder** that can contain this element (e.g. add `fun httpRequest(...)` to `ThreadGroupBuilder`).
3. **JMX extension function** in `jmx/<ElementName>Jmx.kt` — `fun ElementName.toJmxNode(): JmxElement`. This function must only emit the element's own config props; **do not include GUI children** (handled by `toJmxSubtree()`).
4. **Dispatch entry** in `JmxDispatch.kt` — add `is ElementName -> toJmxNode()` to the `when` block in `JMeterElement.toJmxNode()`.

### Builder DSL pattern
```kotlin
// Consumers only need to import the top-level entry point (e.g. testPlan).
// Nested DSL functions are methods on Builder classes, so no additional imports are needed.
val plan = testPlan {
    threadGroup {           // method on TestPlanBuilder
        httpRequest { }     // method on ThreadGroupBuilder
    }
}
```

`JMeterContainerBuilder.build()` automatically wires `children` into the built element after `doBuild()` and before `validate()`. Subclass `doBuild()` implementations must **not** call `children.forEach { element.add(it) }` — this is done by the base class.

### JMX property naming
JMeter XML property names follow the convention `ClassName.propertyName` (e.g. `ThreadGroup.num_threads`). Match these exactly when writing `toJmxNode()` functions — cross-reference `testfile/test.jmx` or official JMeter source.

### children vs configNodes

`JMeterContainer.children` holds **only elements that are visible and addable in the JMeter GUI tree** (e.g. an HTTPSampler inside a ThreadGroup).

XML sub-nodes that belong to an element's own internal configuration — such as the `LoopController` embedded inside `ThreadGroup`, or `stringProp`/`boolProp` nodes — are **not** GUI children. On `AnyElement` these are stored in `configNodes` (type `List<ConfigNode>`).

`ConfigNode` is a separate class with no relation to the `JMeterElement` hierarchy:

```kotlin
data class ConfigNode(
    val tagName: String,
    val attributes: Map<String, String> = emptyMap(),
    val value: String? = null,
    val configNodes: List<ConfigNode> = emptyList()
)
```

When implementing typed elements (e.g. `ThreadGroup`), internal nodes like `LoopController` become dedicated fields on the model class, not entries in `children`.

### Package structure
Library source is under `tools.kenjiotsuka.jmeterk`, with sub-packages `jmx`, `model.core`, `model.thread`, etc. Sample source is under `tools.kenjiotsuka.sample`. Both use `src/main/java/` as the source root despite being pure Kotlin.
