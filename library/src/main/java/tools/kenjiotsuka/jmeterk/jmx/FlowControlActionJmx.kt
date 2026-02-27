package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.sampler.FlowControlAction

fun FlowControlAction.toJmxNode(): JmxElement = JmxElement(
    tag = "TestAction",
    attributes = buildMap {
        put("guiclass", "TestActionGui")
        put("testclass", "TestAction")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = listOf(
        intProp("ActionProcessor.action", action.jmxValue),
        intProp("ActionProcessor.target", target.jmxValue),
        stringProp("ActionProcessor.duration", duration.toString())
    )
)
