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
    children = listOf(
        boolProp("displayJMeterProperties", displayJMeterProperties),
        boolProp("displayJMeterVariables", displayJMeterVariables),
        boolProp("displaySystemProperties", displaySystemProperties)
    )
)
