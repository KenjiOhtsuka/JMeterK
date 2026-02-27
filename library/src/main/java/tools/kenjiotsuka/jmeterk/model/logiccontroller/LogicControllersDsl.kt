package tools.kenjiotsuka.jmeterk.model.logiccontroller

import tools.kenjiotsuka.jmeterk.model.core.JMeterElement

interface LogicControllersDsl {
    fun add(child: JMeterElement)

    fun ifController(block: IfControllerBuilder.() -> Unit) =
        add(IfControllerBuilder().apply(block).build())
}
