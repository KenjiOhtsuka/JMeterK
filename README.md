# JMeterK

A Kotlin DSL library for programmatically building [Apache JMeter](https://jmeter.apache.org/) test plans and serializing them to `.jmx` files.

## Motivation

JMeter test plans are stored as `.jmx` files — verbose XML that is difficult to read, review, and maintain. Editing them through the JMeter GUI makes it hard to:

- **Track changes** — XML diffs are noisy and hard to understand at a glance
- **Review** — Pull request reviews on raw `.jmx` files require deep familiarity with JMeter's XML structure
- **Collaborate** — It is easy to accidentally overwrite a colleague's changes when multiple people edit the same GUI file
- **Modularize** — The GUI treats the test plan as a single monolithic file; there is no way to split it across multiple files or share common parts between plans
- **Reuse** — Common configurations (e.g. a shared set of HTTP requests used by multiple thread groups) must be duplicated by hand in the GUI

JMeterK solves this by letting you define test plans in Kotlin code. The code is human-readable, diffable, and reviewable like any other source file, while the library handles generating the correct `.jmx` XML. Because it is plain Kotlin, you can split large test plans across multiple files and share common request definitions between thread groups — something the JMeter GUI cannot do.

## Features

- Type-safe, readable DSL for constructing JMeter test plans
- Produces valid `.jmx` files that can be opened and run in JMeter as-is
- Covers a wide range of JMeter elements:
  - **Threads**: `ThreadGroup`, `OpenModelThreadGroup`
  - **Samplers**: `HTTPSamplerProxy`, `DebugSampler`, `JSR223Sampler`, `TestAction` (FlowControlAction)
  - **Logic Controllers**: `IfController`, `LoopController`, `WhileController`, `TransactionController`
  - **Assertions**: `ResponseAssertion`, `JSR223Assertion`, `JSONPathAssertion`, `SizeAssertion`, `XPath2Assertion`
  - **Config Elements**: `HttpHeaderManager`
  - **Pre/Post Processors**: `JSR223PreProcessor`, `JSR223PostProcessor`
- **Split across files** — define thread groups and requests in separate files; compose them in `testPlan {}`
- **Share and reuse** — extract common request definitions (e.g. `val loginRequest: HttpRequestBuilder.() -> Unit`) and use them across multiple thread groups
- Escape hatch via `AnyElement` for elements not yet supported by the typed model
- Clean architecture: model layer is pure data; JMX serialization is isolated in a separate package

## Requirements

- JVM 11+
- Kotlin 2.x (2.2+ recommended for multi-dollar string interpolation)

## Installation

Add JitPack to your repositories and declare the dependency:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

// build.gradle.kts
dependencies {
    implementation("com.github.KenjiOhtsuka.JMeterK:library:Tag")
}
```

Replace `Tag` with the desired release tag (e.g. `0.1.1`). See [releases](../../releases).

## Quick Start

```kotlin
val plan = testPlan {
    name = "My Test Plan"

    threadGroup {
        name = "Users"
        numberOfThreads = 10
        rampUpPeriodTime = 5

        httpRequest {
            name = "GET homepage"
            serverNameOrIp = "example.com"
            protocol = HttpRequest.Protocol.HTTPS
            httpRequestMethod = HttpRequest.Method.GET
            path = "/"

            responseAssertion {
                name = "Status 200"
                fieldToTest = ResponseAssertion.FieldToTest.RESPONSE_CODE
                matchingRule = ResponseAssertion.PatternMatchingRule.EQUALS
                patterns.add("200")
            }
        }
    }
}

// Serialize to XML string
val xml: String = buildJmxDocument(plan)

// Write to file
File("my-test.jmx").writeText(xml)
```

## DSL Reference

### TestPlan

```kotlin
testPlan {
    name = "Test Plan"
    functionalMode = false          // default
    serializeThreadGroups = false   // default

    threadGroup { ... }
}
```

### ThreadGroup

```kotlin
threadGroup {
    name = "Thread Group"
    numberOfThreads = 10
    rampUpPeriodTime = 5
    loopCount = 1              // null = infinite
    duration = 60              // seconds; only emitted when set
    startupDelay = 0           // seconds; only emitted when set
    sameUserOnEachIteration = false
    actionToBeTakenAfterSampleError = ActionToBeTakenAfterSampleError.CONTINUE

    httpRequest { ... }
}
```

### HTTPSamplerProxy

```kotlin
httpRequest {
    name = "HTTP Request"
    protocol = HttpRequest.Protocol.HTTPS
    serverNameOrIp = "example.com"
    portNumber = 443            // optional
    path = "/api/data"
    httpRequestMethod = HttpRequest.Method.POST

    // Body data mode
    bodyData = """{"key":"value"}"""

    // Parameters mode (mutually exclusive with bodyData)
    parameters.add(HttpRequest.Parameter("key", "value"))

    responseAssertion { ... }
    jsr223Assertion { ... }
}
```

### ResponseAssertion

```kotlin
responseAssertion {
    name = "Response Assertion"
    fieldToTest = ResponseAssertion.FieldToTest.TEXT_RESPONSE
    matchingRule = ResponseAssertion.PatternMatchingRule.CONTAINS
    not = false
    or = false
    ignoreStatus = false
    patterns.addAll(listOf("expected string"))
    customFailureMessage = ""
}
```

**`FieldToTest` values:** `TEXT_RESPONSE`, `RESPONSE_CODE`, `RESPONSE_MESSAGE`, `RESPONSE_HEADERS`, `REQUEST_HEADERS`, `URL_SAMPLED`, `DOCUMENT_TEXT`, `REQUEST_DATA`

**`PatternMatchingRule` values:** `CONTAINS`, `MATCHES`, `EQUALS`, `SUBSTRING`

### JSR223Assertion

```kotlin
jsr223Assertion {
    name = "JSR223 Assertion"
    language = Jsr223Language.GROOVY  // dropdown selection; import tools.kenjiotsuka.jmeterk.model.jsr223.Jsr223Language
    // customLanguage = "kotlin"       // overrides language when set
    script = "assert vars.get('token') != null"
    parameters = ""
    filename = ""
    cacheCompiledScriptIfAvailable = true
}
```

**`Jsr223Language` values:** `BEAN_SHELL`, `GROOVY`, `JAVA`, `JAVASCRIPT`, `JEXL`, `JEXL2`, `JEXL3`, `NASHORN`

### OpenModelThreadGroup

```kotlin
openModelThreadGroup {
    name = "Open Model Thread Group"
    schedule = "rate(10/sec) random_arrivals(60 sec)"   // Open Model DSL expression
    randomSeed = 0L         // 0 = new random seed every run
    actionToBeTakenAfterSampleError = ActionToBeTakenAfterSampleError.CONTINUE

    httpRequest { ... }
}
```

### IfController

```kotlin
ifController {
    name = "If Controller"
    condition = "\${JMeterThread.last_sample_ok}"
    evaluateAll = false     // evaluate condition for all threads (default: false)
    useExpression = true    // use expression evaluator (default: true)

    httpRequest { ... }
}
```

### HttpHeaderManager

```kotlin
httpHeaderManager {
    name = "HTTP Header Manager"
    header("Content-Type", "application/json")
    header("Authorization", "Bearer \${token}")
}
```

### AnyElement (escape hatch)

For JMeter elements not yet supported by a typed model class:

```kotlin
anyElement {
    tagName = "GenericElement"
    name = "My Element"
    attributes = mapOf(
        "guiclass"  to "GenericElementGui",
        "testclass" to "GenericElement",
        "testname"  to "My Element"
    )
    configNode {
        tagName = "stringProp"
        attributes = mapOf("name" to "GenericElement.property")
        value = "value"
    }
}
```

> **Tip:** once a typed DSL class is available (e.g. `flowControlAction {}`), prefer it over `anyElement`.

## Building

```bash
# Build all modules
./gradlew build

# Run library tests
./gradlew :library:test

# Run sample application
./gradlew :sample:run
```

## Project Structure

```
JMeterK/
  library/   ← the JMeterK library
  sample/    ← runnable usage example
```

## License

MIT — see [LICENSE](LICENSE).
