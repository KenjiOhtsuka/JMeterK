package tools.kenjiotsuka.jmeterk.model.timer

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class ConstantTimer(
    override val name: String,
    override val comment: String,
    /** GUI: "Thread delay (in milliseconds)". JMX: `ConstantTimer.delay`. Default: 300. */
    val delay: Long,
    override val enabled: Boolean,
) : JMeterLeaf(name, comment, enabled)

class ConstantTimerBuilder : JMeterLeafBuilder<ConstantTimer>() {
    override var name: String = "Constant Timer"
    var delay: Long = 300L

    override fun doBuild(): ConstantTimer = ConstantTimer(
        name = name,
        comment = comment,
        delay = delay,
        enabled = enabled,
    )
}
