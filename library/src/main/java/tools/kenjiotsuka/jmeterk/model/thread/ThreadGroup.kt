package tools.kenjiotsuka.jmeterk.model.thread

import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer

data class ThreadGroup(
    override val name: String,
    override val comment: String,
    val actionToBeTakenAfterSampleError: ActionToBeTakenAfterSampleError,
    val numberOfThreads: Int,
    val rampUpPeriodTime: Int,
    val loopCount: Int?,
    val sameUserOnEachIteration: Boolean,
    val delayThreadCreationUntilNeeded: Boolean,
    val specifyThreadLifetime: Boolean,
    val duration: Int?,
    val startupDelay: Int?,
    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled)

class ThreadGroupBuilder : AbstractThreadGroupBuilder<ThreadGroup>() {
    override var name: String = "Thread Group"

    var actionToBeTakenAfterSampleError: ActionToBeTakenAfterSampleError =
        ActionToBeTakenAfterSampleError.CONTINUE
    var numberOfThreads: Int = 1
    /** Ramp-up period in seconds */
    var rampUpPeriodTime: Int = 1
    /** Loop Count. null for infinite loop. */
    var loopCount: Int? = 1
    var sameUserOnEachIteration: Boolean = false
    var delayThreadCreationUntilNeeded: Boolean = false
    var specifyThreadLifetime: Boolean = false
    /** Duration in seconds */
    var duration: Int? = null
    /** Start up delay in seconds */
    var startupDelay: Int? = null

    override fun doBuild(): ThreadGroup = ThreadGroup(
        name,
        comment,
        actionToBeTakenAfterSampleError,
        numberOfThreads,
        rampUpPeriodTime,
        loopCount,
        sameUserOnEachIteration,
        delayThreadCreationUntilNeeded,
        specifyThreadLifetime,
        duration,
        startupDelay,
        enabled
    )
}
