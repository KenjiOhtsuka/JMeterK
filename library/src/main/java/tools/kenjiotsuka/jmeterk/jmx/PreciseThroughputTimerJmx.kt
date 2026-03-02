package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.timer.PreciseThroughputTimer

fun PreciseThroughputTimer.toJmxNode(): JmxElement = JmxElement(
    tag = "PreciseThroughputTimer",
    attributes = buildMap {
        put("guiclass", "TestBeanGUI")
        put("testclass", "PreciseThroughputTimer")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        // allowedThroughputSurplus is always 1.0 (JMeter internal, not user-configurable)
        add(doubleProp("allowedThroughputSurplus", 1.0))
        add(intProp("batchSize", batchSize))
        add(intProp("batchThreadDelay", batchThreadDelay))
        add(longProp("duration", duration))
        // exactLimit is always 10000 (JMeter internal, not user-configurable)
        add(intProp("exactLimit", 10000))
        add(longProp("randomSeed", randomSeed))
        add(doubleProp("throughput", throughput))
        add(intProp("throughputPeriod", throughputPeriod))
    }
)
