package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.assertion.XPath2Assertion

fun XPath2Assertion.toJmxNode(): JmxElement = JmxElement(
    tag = "XPath2Assertion",
    attributes = buildMap {
        put("guiclass", "XPath2AssertionGui")
        put("testclass", "XPath2Assertion")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        add(boolProp("XPath.negate", invertAssertion))
        add(stringProp("XPath.xpath", xPath))
        add(stringProp("XPath.namespaces", namespacesAliasesList))
        when (applyTo) {
            ApplyTo.MAIN_SAMPLE_AND_SUB_SAMPLES ->
                add(stringProp("Assertion.scope", "all"))
            ApplyTo.SUB_SAMPLES_ONLY ->
                add(stringProp("Assertion.scope", "children"))
            ApplyTo.JMETER_VARIABLE -> {
                add(stringProp("Assertion.scope", "variable"))
                add(stringProp("Scope.variable", jmeterVariableName))
            }
            ApplyTo.MAIN_SAMPLE_ONLY -> { /* default — Assertion.scope not emitted */ }
        }
    }
)
