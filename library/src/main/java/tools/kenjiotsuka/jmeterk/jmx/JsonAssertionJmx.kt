package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.JsonAssertion

fun JsonAssertion.toJmxNode(): JmxElement = JmxElement(
    tag = "JSONPathAssertion",
    attributes = buildMap {
        put("guiclass", "JSONPathAssertionGui")
        put("testclass", "JSONPathAssertion")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = listOf(
        stringProp("JSON_PATH", jsonPath),
        stringProp("EXPECTED_VALUE", expectedValue),
        boolProp("JSONVALIDATION", additionallyAssertValue),
        boolProp("EXPECT_NULL", expectNull),
        boolProp("INVERT", invertAssertion),
        boolProp("ISREGEX", matchAsRegularExpression)
    )
)
