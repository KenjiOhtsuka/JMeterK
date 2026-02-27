package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.core.TestPlan

fun TestPlan.toJmxNode(): JmxElement {
    val userDefinedVars = elementProp(
        "TestPlan.user_defined_variables", "Arguments",
        "ArgumentsPanel", "Arguments", "User Defined Variables",
        listOf(JmxElement("collectionProp", mapOf("name" to "Arguments.arguments")))
    )
    val attrs = buildMap {
        put("guiclass", "TestPlanGui"); put("testclass", "TestPlan"); put("testname", name)
        if (!enabled) put("enabled", "false")
    }
    return JmxElement(
        tag = "TestPlan",
        attributes = attrs,
        children = buildList {
            add(userDefinedVars)
            if (functionalMode) add(boolProp("TestPlan.functional_mode", true))
            if (serializeThreadGroups) add(boolProp("TestPlan.serialize_threadgroups", true))
        }
    )
}
