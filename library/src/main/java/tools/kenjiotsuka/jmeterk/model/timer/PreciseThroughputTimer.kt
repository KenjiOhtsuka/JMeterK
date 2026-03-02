package tools.kenjiotsuka.jmeterk.model.timer

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class PreciseThroughputTimer(
    override val name: String,
    override val comment: String,
    /** GUI: "Target throughput (in samples per 'throughput period')". JMX: `throughput`. Default: 100.0. */
    val throughput: Double,
    /** GUI: "Throughput period (seconds)". JMX: `throughputPeriod`. Default: 3600. */
    val throughputPeriod: Int,
    /** GUI: "Test duration (seconds)". JMX: `duration`. Default: 3600. */
    val duration: Long,
    /** GUI: "Number of threads in the batch (threads)". JMX: `batchSize`. Default: 1. */
    val batchSize: Int,
    /** GUI: "Delay between threads in the batch (ms)". JMX: `batchThreadDelay`. Default: 0. */
    val batchThreadDelay: Int,
    /** GUI: "Random seed (change from 0 to random)". JMX: `randomSeed`. Default: 0. */
    val randomSeed: Long,
    override val enabled: Boolean,
) : JMeterLeaf(name, comment, enabled)

class PreciseThroughputTimerBuilder : JMeterLeafBuilder<PreciseThroughputTimer>() {
    override var name: String = "Precise Throughput Timer"
    var throughput: Double = 100.0
    var throughputPeriod: Int = 3600
    var duration: Long = 3600L
    var batchSize: Int = 1
    var batchThreadDelay: Int = 0
    var randomSeed: Long = 0L

    override fun doBuild(): PreciseThroughputTimer = PreciseThroughputTimer(
        name = name,
        comment = comment,
        throughput = throughput,
        throughputPeriod = throughputPeriod,
        duration = duration,
        batchSize = batchSize,
        batchThreadDelay = batchThreadDelay,
        randomSeed = randomSeed,
        enabled = enabled,
    )
}
