# Contributing to JMeterK

Thank you for your interest in contributing!

## Development Setup

```bash
git clone <repo>
cd JMeterK
./gradlew build
```

Requirements: JVM 21+ for building (library bytecode targets JVM 11+), Kotlin 2.x.

## Project Structure

```
JMeterK/
  library/
    src/main/java/tools/kenjiotsuka/jmeterk/
      jmx/        ← JMX serialization (extension functions, converter)
      model/      ← domain model + DSL builders
        core/     ← JMeterElement base classes, TestPlan, AnyElement
        thread/   ← ThreadGroup, OpenModelThreadGroup, AbstractThreadGroupBuilder, ThreadsDsl
        sampler/  ← HttpRequest, SamplersDsl
        assertion/← ResponseAssertion, Jsr223Assertion, AssertionsDsl
        configelement/ ← HttpHeaderManager, ConfigElementsDsl
        logiccontroller/ ← IfController, AbstractLogicControllerBuilder, LogicControllersDsl
        ...       ← other JMeter categories (empty, awaiting implementation)
    src/test/
      java/       ← test sources
      resources/  ← test.jmx reference file
  sample/
    src/main/java/tools/kenjiotsuka/sample/
      Main.kt     ← runnable usage example
```

## Architecture Overview

The library has two distinct layers:

- **`model/`** — Pure data classes and DSL builders. No knowledge of any output format.
- **`jmx/`** — JMX serialization as extension functions. One file per element (e.g. `ThreadGroupJmx.kt`).

This separation means new output formats (YAML, etc.) can be added as new packages without touching model classes.

### Key files

| File | Role |
|---|---|
| `model/core/JMeterElement.kt` | Base class hierarchy and builder template method |
| `jmx/JmxElement.kt` | `JmxNode` sealed class (`JmxElement`, `JmxText`, `JmxHashTree`) |
| `jmx/JmxDispatch.kt` | Routing (`toJmxNode()`), subtree building (`toJmxSubtree()`), document entry point (`buildJmxDocument()`) |
| `jmx/JmxConverter.kt` | `JmxNode → String` with HTML escaping |

## Adding a New JMeter Element

Every new element requires four pieces. Use `ThreadGroup` and `ThreadGroupJmx.kt` as a reference.

### 1. Model class — `model/<category>/<ElementName>.kt`

```kotlin
data class MyElement(
    override val name: String,
    override val comment: String,
    val myField: String,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled)   // or JMeterContainer for elements with children
```

Field names must match the **JMeter GUI label** (not the JMX attribute name).

### 2. Builder class — same file

```kotlin
class MyElementBuilder : JMeterLeafBuilder<MyElement>() {  // or JMeterContainerBuilder
    override var name: String = "My Element"
    var myField: String = ""

    override fun doBuild(): MyElement = MyElement(
        name = name, comment = comment, myField = myField, enabled = enabled
    )
    // For JMeterContainerBuilder, do NOT call children.forEach { element.add(it) } —
    // the base class handles children wiring automatically.
}
```

Add a DSL function to the appropriate **category DSL interface** so all builders that support that category automatically gain the new DSL function:

```kotlin
// in model/<category>/CategoryDsl.kt:
interface ConfigElementsDsl {
    fun add(child: JMeterElement)

    fun myElement(block: MyElementBuilder.() -> Unit) =
        add(MyElementBuilder().apply(block).build())
}
```

Then implement the interface on the relevant abstract builder(s):
- Elements that belong in threads/logic controllers → implement on `AbstractThreadGroupBuilder` and/or `AbstractLogicControllerBuilder`
- Assertions / config elements on `HttpRequestBuilder` → implement `AssertionsDsl` / `ConfigElementsDsl` directly

If the element is a new thread group variant, add it to `ThreadsDsl` and implement `ThreadsDsl` on `TestPlanBuilder`.

### 3. JMX extension function — `jmx/<ElementName>Jmx.kt`

```kotlin
fun MyElement.toJmxNode(): JmxElement {
    val attrs = buildMap {
        put("guiclass", "MyElementGui")
        put("testclass", "MyElement")
        put("testname", name)
        if (!enabled) put("enabled", "false")   // only emit when false
    }
    return JmxElement(
        tag = "MyElement",
        attributes = attrs,
        children = listOf(
            stringProp("MyElement.my_field", myField)
            // config props only — do NOT include GUI children here
        )
    )
}
```

Rules:
- The `enabled` attribute is **only emitted when `false`**.
- `toJmxNode()` must return **only the element's own config properties**. GUI children are placed in the sibling `<hashTree>` by `toJmxSubtree()` automatically — never include them here.
- JMX property names follow `ClassName.property_name` convention. Cross-reference `src/test/resources/test.jmx` or JMeter source.

### 4. Dispatch entry — `jmx/JmxDispatch.kt`

```kotlin
is MyElement -> toJmxNode()
```

Add this line to the `when` expression in `JMeterElement.toJmxNode()`.

## Naming Conventions

| Concern | Convention |
|---|---|
| Model field names | Match the **JMeter GUI label** (e.g. `ignoreStatus`, `cacheCompiledScriptIfAvailable`) |
| JMX attribute names | Handled in the serializer, match JMeter JMX spec (e.g. `Assertion.assume_success`, `cacheKey`) |
| Enum values | SCREAMING_SNAKE_CASE per Kotlin conventions |
| Enums mapping to JMX strings | Carry a property for the JMX value (e.g. `Language.GROOVY` has `scriptLanguage = "groovy"`) |

## Testing

The integration test `TestPlanSerializationTest` verifies that the DSL output exactly matches `src/test/resources/test.jmx` (after whitespace normalization). When adding a new element:

1. Add it to the DSL in `TestPlanSerializationTest` if it appears in `test.jmx`.
2. Update `src/test/resources/test.jmx` if you are adding a new reference example.
3. Run `./gradlew :library:test` to verify.

## Pull Requests

- Keep each PR focused on a single element or concern.
- Ensure `./gradlew :library:test` passes.
- Follow the four-step checklist above for new elements.
