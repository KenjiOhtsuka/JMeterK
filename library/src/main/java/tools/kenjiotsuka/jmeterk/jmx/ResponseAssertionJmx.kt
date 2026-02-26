package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.ResponseAssertion

fun ResponseAssertion.toJmxNode(): JmxElement {
    val testField = when (fieldToTest) {
        ResponseAssertion.FieldToTest.TEXT_RESPONSE    -> "Assertion.response_data"
        ResponseAssertion.FieldToTest.RESPONSE_CODE    -> "Assertion.response_code"
        ResponseAssertion.FieldToTest.RESPONSE_MESSAGE -> "Assertion.response_message"
        ResponseAssertion.FieldToTest.RESPONSE_HEADERS -> "Assertion.response_headers"
        ResponseAssertion.FieldToTest.REQUEST_HEADERS  -> "Assertion.request_headers"
        ResponseAssertion.FieldToTest.URL_SAMPLED      -> "Assertion.sample_label"
        ResponseAssertion.FieldToTest.DOCUMENT_TEXT    -> "Assertion.response_data_as_document"
        ResponseAssertion.FieldToTest.REQUEST_DATA     -> "Assertion.request_data"
    }

    val ruleValue = when (patternMatchingRule) {
        ResponseAssertion.PatternMatchingRule.CONTAINS  -> 2
        ResponseAssertion.PatternMatchingRule.MATCHES   -> 1
        ResponseAssertion.PatternMatchingRule.EQUALS    -> 8
        ResponseAssertion.PatternMatchingRule.SUBSTRING -> 16
    }
    val testType = ruleValue + (if (not) 4 else 0) + (if (or) 32 else 0)

    // "Asserion" is an intentional typo in JMeter source that must be preserved
    val testStrings = JmxElement(
        tag = "collectionProp",
        attributes = mapOf("name" to "Asserion.test_strings"),
        children = patterns.map { pattern -> stringProp(pattern.hashCode().toString(), pattern) }
    )

    return JmxElement(
        tag = "ResponseAssertion",
        attributes = buildMap {
            put("guiclass", "AssertionGui"); put("testclass", "ResponseAssertion"); put("testname", name)
            if (!enabled) put("enabled", "false")
        },
        children = listOf(
            testStrings,
            stringProp("Assertion.custom_message", customFailureMessage),
            stringProp("Assertion.test_field", testField),
            boolProp("Assertion.assume_success", ignoreStatus),
            intProp("Assertion.test_type", testType)
        )
    )
}
