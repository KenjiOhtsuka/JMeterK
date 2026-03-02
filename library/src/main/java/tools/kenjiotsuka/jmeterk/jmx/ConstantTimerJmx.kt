package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.timer.ConstantTimer

fun ConstantTimer.toJmxNode(): JmxElement = JmxElement(
    tag = "ConstantTimer",
    attributes = buildMap {
        put("guiclass", "ConstantTimerGui")
        put("testclass", "ConstantTimer")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(stringProp("ConstantTimer.delay", delay.toString()))
    }
)
