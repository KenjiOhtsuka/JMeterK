package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.assertion.SizeAssertion

fun SizeAssertion.toJmxNode(): JmxElement {
    val sizeValue = size  // capture before buildList to avoid shadowing by List.size
    val testField = when (responseField) {
        SizeAssertion.ResponseField.FULL_RESPONSE    -> "SizeAssertion.response_network_size"
        SizeAssertion.ResponseField.RESPONSE_HEADERS -> "SizeAssertion.response_headers"
        SizeAssertion.ResponseField.RESPONSE_BODY    -> "SizeAssertion.response_data"
        SizeAssertion.ResponseField.RESPONSE_CODE    -> "SizeAssertion.response_code"
        SizeAssertion.ResponseField.RESPONSE_MESSAGE -> "SizeAssertion.response_message"
    }
    return JmxElement(
        tag = "SizeAssertion",
        attributes = buildMap {
            put("guiclass", "SizeAssertionGui")
            put("testclass", "SizeAssertion")
            put("testname", name)
            if (!enabled) put("enabled", "false")
        },
        children = buildList {
            add(stringProp("Assertion.test_field", testField))
            add(stringProp("SizeAssertion.size", sizeValue?.toString() ?: ""))
            add(intProp("SizeAssertion.operator", comparisonOperator.jmxValue))
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
}
