package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.logiccontroller.IfController

fun IfController.toJmxNode(): JmxElement = JmxElement(
    tag = "IfController",
    attributes = buildMap {
        put("guiclass", "IfControllerPanel")
        put("testclass", "IfController")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = listOf(
        stringProp("IfController.condition", condition),
        boolProp("IfController.evaluateAll", evaluateAll),
        boolProp("IfController.useExpression", useExpression)
    )
)
