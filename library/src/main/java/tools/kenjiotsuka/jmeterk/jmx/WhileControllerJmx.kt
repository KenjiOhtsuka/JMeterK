package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.logiccontroller.WhileController

fun WhileController.toJmxNode(): JmxElement = JmxElement(
    tag = "WhileController",
    attributes = buildMap {
        put("guiclass", "WhileControllerGui")
        put("testclass", "WhileController")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = listOf(
        stringProp("WhileController.condition", condition)
    )
)
