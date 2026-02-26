package tools.kenjiotsuka.jmeterk.model.thread

import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainerBuilder
import tools.kenjiotsuka.jmeterk.model.core.JMeterElement
import kotlin.concurrent.thread

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
) : JMeterContainer(name, comment, enabled) {
    enum class ActionToBeTakenAfterSampleError {
        Continue,
        StartNextThreadLoop,
        StopThread,
        StopTest,
        StopTestNow
    }
}

class ThreadGroupBuilder : JMeterContainerBuilder<ThreadGroup>() {
    override var name: String = "Thread Group"

    var actionToBeTakenAfterSampleError: ThreadGroup.ActionToBeTakenAfterSampleError =
        ThreadGroup.ActionToBeTakenAfterSampleError.Continue
    var numberOfThreads: Int = 1
    /** Ramp-ip period in second */
    var rampUpPeriodTime: Int = 1
    /** Loop Count. null for infinite loop. */
    var loopCount: Int? = 1
    var sameUserOnEachIteration: Boolean = false
    var delayThreadCreationUntilNeeded: Boolean = false
    var specifyThreadLifetime: Boolean = false
    /** duratoin in second */
    var duration: Int? = null
    /** Start up delay in second */
    var startupDelay: Int? = null

    override fun doBuild(): ThreadGroup {
        val tg = ThreadGroup(
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
        children.forEach { tg.add(it) }
        return tg
    }
}

fun threadGroup(block: ThreadGroupBuilder.() -> Unit): ThreadGroup {
    return ThreadGroupBuilder().apply(block).build()
}
