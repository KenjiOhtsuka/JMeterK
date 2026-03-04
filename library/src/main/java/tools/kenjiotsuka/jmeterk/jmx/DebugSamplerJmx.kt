package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.sampler.DebugSampler

fun DebugSampler.toJmxNode(): JmxElement = JmxElement(
    tag = "DebugSampler",
    attributes = buildMap {
        put("guiclass", "TestBeanGUI")
        put("testclass", "DebugSampler")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(boolProp("displayJMeterProperties", displayJMeterProperties))
        add(boolProp("displayJMeterVariables", displayJMeterVariables))
        add(boolProp("displaySystemProperties", displaySystemProperties))
    }
)
