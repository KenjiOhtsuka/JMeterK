package tools.kenjiotsuka.jmeterk.model.thread

import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer

data class OpenModelThreadGroup(
    override val name: String,
    override val comment: String,
    /** Action to take when a sample error occurs. */
    val actionToBeTakenAfterSampleError: ActionToBeTakenAfterSampleError,
    /**
     * Schedule expression using the Open Model DSL.
     * Example: `rate(1/sec) random_arrivals(60 sec) pause(5 sec)`
     * Empty string means no schedule is set.
     */
    val schedule: String,
    /**
     * Seed for reproducible workloads. 0 means a new random seed on every execution.
     */
    val randomSeed: Long,
    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled)

class OpenModelThreadGroupBuilder : AbstractThreadGroupBuilder<OpenModelThreadGroup>() {
    override var name: String = "Open Model Thread Group"

    /** Action to take when a sample error occurs. */
    var actionToBeTakenAfterSampleError: ActionToBeTakenAfterSampleError =
        ActionToBeTakenAfterSampleError.CONTINUE

    /**
     * Schedule expression using the Open Model DSL.
     * Example: `rate(1/sec) random_arrivals(60 sec) pause(5 sec)`
     */
    var schedule: String = ""

    /** Seed for reproducible workloads. 0 means a new random seed on every execution. */
    var randomSeed: Long = 0L

    override fun doBuild(): OpenModelThreadGroup = OpenModelThreadGroup(
        name = name,
        comment = comment,
        actionToBeTakenAfterSampleError = actionToBeTakenAfterSampleError,
        schedule = schedule,
        randomSeed = randomSeed,
        enabled = enabled
    )
}
