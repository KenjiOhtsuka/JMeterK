package tools.kenjiotsuka.jmeterk.model.timer

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class UniformRandomTimer(
    override val name: String,
    override val comment: String,
    /**
     * GUI: "Random Delay Maximum (in milliseconds)". JMX: `RandomTimer.range`.
     * Default: 100.0.
     */
    val randomDelayMaximum: Double,
    /**
     * GUI: "Constant Delay Offset (in milliseconds)". JMX: `ConstantTimer.delay`.
     * Default: 0.
     */
    val constantDelayOffset: Long,
    override val enabled: Boolean,
) : JMeterLeaf(name, comment, enabled)

class UniformRandomTimerBuilder : JMeterLeafBuilder<UniformRandomTimer>() {
    override var name: String = "Uniform Random Timer"
    var randomDelayMaximum: Double = 100.0
    var constantDelayOffset: Long = 0L

    override fun doBuild(): UniformRandomTimer = UniformRandomTimer(
        name = name,
        comment = comment,
        randomDelayMaximum = randomDelayMaximum,
        constantDelayOffset = constantDelayOffset,
        enabled = enabled,
    )
}
