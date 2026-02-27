package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.logiccontroller.TransactionController

fun TransactionController.toJmxNode(): JmxElement = JmxElement(
    tag = "TransactionController",
    attributes = buildMap {
        put("guiclass", "TransactionControllerGui")
        put("testclass", "TransactionController")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        add(boolProp("TransactionController.includeTimers", includeTimers))
        // TransactionController.parent is only emitted when true (false is JMeter default)
        if (generateParentSample) add(boolProp("TransactionController.parent", true))
    }
)
