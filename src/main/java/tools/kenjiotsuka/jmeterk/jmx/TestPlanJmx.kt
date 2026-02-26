package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.core.TestPlan

fun TestPlan.toJmxNode(): JmxElement = JmxElement(
    tag = "TestPlan",
    attributes = mapOf(
        "guiclass"  to "TestPlanGui",
        "testclass" to "TestPlan",
        "testname"  to name,
        "enabled"   to enabled.toString()
    ),
    children = children.map { it.toJmxNode() }
)
