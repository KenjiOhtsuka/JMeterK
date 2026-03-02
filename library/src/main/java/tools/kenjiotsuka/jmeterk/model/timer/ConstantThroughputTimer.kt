package tools.kenjiotsuka.jmeterk.model.timer

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class ConstantThroughputTimer(
    override val name: String,
    override val comment: String,
    /** GUI: "Target throughput (in samples per minute)". JMX: `throughput`. Default: 0.0. */
    val throughput: Double,
    /** GUI: "Calculate Throughput based on" dropdown. JMX: `calcMode`. Default: [CalcMode.THIS_THREAD_ONLY]. */
    val calcMode: CalcMode,
    override val enabled: Boolean,
) : JMeterLeaf(name, comment, enabled) {

    /** GUI: "Calculate Throughput based on" dropdown choices. JMX: `calcMode` intProp. */
    enum class CalcMode(val jmxValue: Int) {
        /** GUI: "this thread only". JMX: `0`. */
        THIS_THREAD_ONLY(0),
        /** GUI: "all active threads". JMX: `1`. */
        ALL_ACTIVE_THREADS(1),
        /** GUI: "all active threads in current thread group". JMX: `2`. */
        ALL_ACTIVE_THREADS_IN_THREAD_GROUP(2),
        /** GUI: "all active threads (shared)". JMX: `3`. */
        ALL_ACTIVE_THREADS_SHARED(3),
        /** GUI: "all active threads in current thread group (shared)". JMX: `4`. */
        ALL_ACTIVE_THREADS_IN_THREAD_GROUP_SHARED(4),
    }
}

class ConstantThroughputTimerBuilder : JMeterLeafBuilder<ConstantThroughputTimer>() {
    override var name: String = "Constant Throughput Timer"
    var throughput: Double = 0.0
    var calcMode: ConstantThroughputTimer.CalcMode = ConstantThroughputTimer.CalcMode.THIS_THREAD_ONLY

    override fun doBuild(): ConstantThroughputTimer = ConstantThroughputTimer(
        name = name,
        comment = comment,
        throughput = throughput,
        calcMode = calcMode,
        enabled = enabled,
    )
}
