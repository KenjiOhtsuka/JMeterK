package tools.kenjiotsuka.jmeterk.model.logiccontroller

import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer

data class TransactionController(
    override val name: String,
    override val comment: String,
    /**
     * Generate a parent sample that wraps all child samples (GUI: "Generate parent sample").
     * When `false`, an additional total sample is generated after nested samples.
     */
    val generateParentSample: Boolean,
    /**
     * Include time spent in timers and pre/post processors in the generated sample duration
     * (GUI: "Include duration of timer and pre-post processors in generated sample").
     */
    val includeTimers: Boolean,
    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled)

class TransactionControllerBuilder : AbstractLogicControllerBuilder<TransactionController>() {
    override var name: String = "Transaction Controller"
    /** Generate a parent sample wrapping all child samples. Default: false. */
    var generateParentSample: Boolean = false
    /** Include timer and pre/post processor time in the sample duration. Default: false. */
    var includeTimers: Boolean = false

    override fun doBuild(): TransactionController = TransactionController(
        name = name,
        comment = comment,
        generateParentSample = generateParentSample,
        includeTimers = includeTimers,
        enabled = enabled
    )
}
