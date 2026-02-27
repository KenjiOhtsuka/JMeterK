package tools.kenjiotsuka.jmeterk.model.logiccontroller

import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer

data class LoopController(
    override val name: String,
    override val comment: String,
    /**
     * Number of loop iterations. `null` means infinite (maps to `LoopController.loops = -1`).
     * Negative values are normalized to `null` at build time, matching JMeter GUI behavior.
     */
    val loopCount: Int?,
    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled)

class LoopControllerBuilder : AbstractLogicControllerBuilder<LoopController>() {
    override var name: String = "Loop Controller"
    /**
     * Number of iterations. `null` or any negative value means infinite loop.
     * Negative values are automatically normalized to `null` (i.e. infinite), matching
     * JMeter GUI behavior where any value < 0 is treated as -1 (infinite). Default: 1.
     */
    var loopCount: Int? = 1

    /**
     * When `true`, the loop runs indefinitely, regardless of [loopCount].
     * Takes priority over [loopCount].
     */
    var infinite: Boolean = false

    override fun doBuild(): LoopController = LoopController(
        name = name,
        comment = comment,
        loopCount = if (infinite) null else loopCount?.takeIf { it >= 0 }, // normalize negative → infinite
        enabled = enabled
    )
}
