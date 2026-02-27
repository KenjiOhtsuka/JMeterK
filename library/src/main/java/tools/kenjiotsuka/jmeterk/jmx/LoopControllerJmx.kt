package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.logiccontroller.LoopController

fun LoopController.toJmxNode(): JmxElement = JmxElement(
    tag = "LoopController",
    attributes = buildMap {
        put("guiclass", "LoopControlPanel")
        put("testclass", "LoopController")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        add(intProp("LoopController.loops", loopCount ?: -1))
        if (loopCount != null) add(boolProp("LoopController.continue_forever", false))
    }
)
