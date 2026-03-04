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
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(intProp("ActionProcessor.action", action.jmxValue))
        add(intProp("ActionProcessor.target", target.jmxValue))
        add(stringProp("ActionProcessor.duration", duration.toString()))
    }
)
