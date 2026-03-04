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
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(stringProp("JSON_PATH", jsonPath))
        add(stringProp("EXPECTED_VALUE", expectedValue))
        add(boolProp("JSONVALIDATION", additionallyAssertValue))
        add(boolProp("EXPECT_NULL", expectNull))
        add(boolProp("INVERT", invertAssertion))
        add(boolProp("ISREGEX", matchAsRegularExpression))
    }
)
