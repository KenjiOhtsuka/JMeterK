package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.timer.ConstantThroughputTimer

fun ConstantThroughputTimer.toJmxNode(): JmxElement = JmxElement(
    tag = "ConstantThroughputTimer",
    attributes = buildMap {
        put("guiclass", "TestBeanGUI")
        put("testclass", "ConstantThroughputTimer")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(intProp("calcMode", calcMode.jmxValue))
        add(doubleProp("throughput", throughput))
    }
)
