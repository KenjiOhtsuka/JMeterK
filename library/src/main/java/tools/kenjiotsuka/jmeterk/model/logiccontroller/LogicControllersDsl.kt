package tools.kenjiotsuka.jmeterk.model.logiccontroller

import tools.kenjiotsuka.jmeterk.model.core.JMeterElement

interface LogicControllersDsl {
    fun add(child: JMeterElement)

    fun ifController(block: IfControllerBuilder.() -> Unit) =
        add(IfControllerBuilder().apply(block).build())

    fun loopController(block: LoopControllerBuilder.() -> Unit) =
        add(LoopControllerBuilder().apply(block).build())

    fun whileController(block: WhileControllerBuilder.() -> Unit) =
        add(WhileControllerBuilder().apply(block).build())

    fun transactionController(block: TransactionControllerBuilder.() -> Unit) =
        add(TransactionControllerBuilder().apply(block).build())
}
