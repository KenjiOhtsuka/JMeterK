package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.timer.UniformRandomTimer

fun UniformRandomTimer.toJmxNode(): JmxElement = JmxElement(
    tag = "UniformRandomTimer",
    attributes = buildMap {
        put("guiclass", "UniformRandomTimerGui")
        put("testclass", "UniformRandomTimer")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(stringProp("ConstantTimer.delay", constantDelayOffset.toString()))
        add(stringProp("RandomTimer.range", randomDelayMaximum.toString()))
    }
)
