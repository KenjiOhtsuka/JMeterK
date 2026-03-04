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
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(stringProp("IfController.condition", condition))
        add(boolProp("IfController.evaluateAll", evaluateAll))
        add(boolProp("IfController.useExpression", useExpression))
    }
)
