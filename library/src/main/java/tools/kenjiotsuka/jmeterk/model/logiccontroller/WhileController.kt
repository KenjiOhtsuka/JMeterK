package tools.kenjiotsuka.jmeterk.model.logiccontroller

import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer

data class WhileController(
    override val name: String,
    override val comment: String,
    /** The condition expression. The loop runs while this evaluates to non-blank and non-"false". */
    val condition: String,
    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled)

class WhileControllerBuilder : AbstractLogicControllerBuilder<WhileController>() {
    override var name: String = "While Controller"
    /** Condition expression (e.g. `"\${myVar} != 'done'"`). Loop runs while non-blank and non-"false". */
    var condition: String = ""

    override fun doBuild(): WhileController = WhileController(
        name = name,
        comment = comment,
        condition = condition,
        enabled = enabled
    )
}
