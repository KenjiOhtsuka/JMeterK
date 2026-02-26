# JMeterK Copilot Instructions

## Project Overview

JMeterK is a Kotlin DSL library for programmatically constructing JMeter test plans and serializing them to `.jmx` XML files. It mirrors the JMeter element hierarchy in Kotlin with a builder/DSL pattern.

## Build & Test Commands

```bash
# Build
./gradlew build

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "tools.kenjiotsuka.jmeterk.SomeTest"

# Run a single test method
./gradlew test --tests "tools.kenjiotsuka.jmeterk.SomeTest.methodName"
```

- Kotlin 2.3.0, JVM toolchain 21
- JUnit Jupiter (JUnit 5) for tests

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
  - Empty packages exist for future elements: `assertion/`, `configelement/`, `listener/`, `nontestelement/`, `postprocessor/`, `preprocessors/`, `testfragment/`, `timer/`

The directory structure and classnames are based on JMeter GUI.
The attributes of the classes are based on inputs of JMeter GUI.

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
- `JmxElement.kt` — `sealed class JmxNode` with `JmxElement` and `JmxText`; property helper fns: `stringProp`, `boolProp`, `intProp`, `longProp`, `elementProp`
- `JmxConverter.kt` — renders a `JmxNode` tree to an XML string with HTML escaping (`JmxNode → String`)
- `JmxWriter.kt` — delegates to `JmxConverter` and writes the result to a `File` (`JmxNode → File`); use `JmxConverter` directly when only a string is needed (e.g. in tests)
- `JmxDispatch.kt` — single `when` expression routing any `JMeterElement` to its `toJmxNode()` extension fn
- Per-element extension functions (`TestPlanJmx.kt`, `ThreadGroupJmx.kt`) — convert model objects to `JmxElement` trees

#### Serialization design

Model classes in `model/` are pure data and have no knowledge of any output format. Serialization logic lives in `jmx/` as **extension functions**, one file per element type (e.g. `fun ThreadGroup.toJmxNode(): JmxElement` in `ThreadGroupJmx.kt`). This keeps `model/` free of format-specific code and allows new output formats (YAML, etc.) to be added as separate packages without touching model classes.

The export pipeline is: `JMeterElement → JmxNode (intermediate) → String → File`

### Reference file
`testfile/test.jmx` is a hand-authored JMX file showing the expected XML output structure, including the `<hashTree>` wrapping pattern used by JMeter.

## Key Conventions

### Adding a new JMeter element
Every new element requires these four pieces:
1. **Model class** in `model/<category>/` extending `JMeterContainer` or `JMeterLeaf`
2. **Builder class** in the same file extending `JMeterContainerBuilder` or `JMeterLeafBuilder`, with a top-level DSL function (e.g. `fun threadGroup(block: ThreadGroupBuilder.() -> Unit): ThreadGroup`)
3. **JMX extension function** in `jmx/<ElementName>Jmx.kt` — `fun ElementName.toJmxNode(): JmxElement`
4. **Dispatch entry** in `JmxDispatch.kt` — add `is ElementName -> toJmxNode()` to the `when` block

### Builder DSL pattern
```kotlin
// Consumers use nested lambdas; builders collect children via the inherited `children` list
val plan = threadGroup {
    numberOfThreads = 5
    threadGroup { }  // nested element added to children
}
```
Builders call `children.forEach { result.add(it) }` in `build()` to transfer collected children to the immutable model object.

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
All source under `tools.kenjiotsuka.jmeterk`, with sub-packages `jmx`, `model.core`, `model.thread`, etc. The source root is `src/main/java/` (not `src/main/kotlin/`) despite the project being pure Kotlin.


