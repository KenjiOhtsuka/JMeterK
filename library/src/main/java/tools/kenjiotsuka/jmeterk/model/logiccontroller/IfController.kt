package tools.kenjiotsuka.jmeterk.model.logiccontroller

import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer

data class IfController(
    override val name: String,
    override val comment: String,
    /** The condition expression evaluated to decide whether to execute children. */
    val condition: String,
    /** If true, evaluate the condition for each child element instead of once. */
    val evaluateAll: Boolean,
    /** If true, interpret [condition] as a Jexl3 / JavaScript expression rather than a JMeter function. */
    val useExpression: Boolean,
    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled)

class IfControllerBuilder : AbstractLogicControllerBuilder<IfController>() {
    override var name: String = "If Controller"
    /** Condition expression (e.g. `"\${myVar} == 'foo'"` or a Jexl3 expression). */
    var condition: String = ""
    /** Evaluate condition for every child element. Default: false (evaluate once). */
    var evaluateAll: Boolean = false
    /** Use Jexl3/JavaScript expression syntax. Default: true. */
    var useExpression: Boolean = true

    override fun doBuild(): IfController = IfController(
        name = name,
        comment = comment,
        condition = condition,
        evaluateAll = evaluateAll,
        useExpression = useExpression,
        enabled = enabled
    )
}
