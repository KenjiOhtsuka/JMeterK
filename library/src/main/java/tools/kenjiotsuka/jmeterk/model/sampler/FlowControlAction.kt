package tools.kenjiotsuka.jmeterk.model.sampler

import tools.kenjiotsuka.jmeterk.model.assertion.AssertionsDsl
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainerBuilder

data class FlowControlAction(
    override val name: String,
    override val comment: String,
    /**
     * The logical action to perform on the thread.
     * GUI: 6 radio buttons in one group ("Logical Action on Thread" + "Logical Action on Thread/Text").
     * Default: [Action.PAUSE].
     */
    val action: Action,
    /**
     * Target threads for [Action.STOP] and [Action.STOP_NOW].
     * GUI: "Target" drop-down (enabled only when action is Stop or Stop Now). Value always serialized.
     * Default: [Target.CURRENT_THREAD].
     */
    val target: Target,
    /**
     * Pause duration in milliseconds for [Action.PAUSE].
     * GUI: "Duration (milliseconds)" field (enabled only when action is Pause). Value always serialized.
     * Default: 0.
     */
    val duration: Long,
    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled) {

    /** GUI: 6-way radio button group combining "Logical Action on Thread" and "Logical Action on Thread/Text". */
    enum class Action(val jmxValue: Int) {
        /** Stop the thread/test (GUI: "Stop"). JMX value: 0. */
        STOP(0),
        /** Pause execution (GUI: "Pause"). JMX value: 1. Default. */
        PAUSE(1),
        /** Immediately stop the thread/test (GUI: "Stop Now"). JMX value: 2. */
        STOP_NOW(2),
        /** Restart the thread's main loop (GUI: "Start next Thread Loop"). JMX value: 3. */
        START_NEXT_THREAD_LOOP(3),
        /** Jump to the next iteration of the innermost loop (GUI: "Go to next iteration of Current Loop"). JMX value: 4. */
        GO_TO_NEXT_ITERATION_OF_CURRENT_LOOP(4),
        /** Exit the innermost loop (GUI: "Break Current Loop"). JMX value: 5. */
        BREAK_CURRENT_LOOP(5)
    }

    /** GUI: "Target" drop-down (only relevant for [Action.STOP] and [Action.STOP_NOW]). */
    enum class Target(val jmxValue: Int) {
        /** Stop this thread only (GUI: "Current Thread"). JMX value: 0. Default. */
        CURRENT_THREAD(0),
        /** Stop all threads in the test (GUI: "All Threads"). JMX value: 2. */
        ALL_THREADS(2)
    }
}

class FlowControlActionBuilder : JMeterContainerBuilder<FlowControlAction>(), AssertionsDsl {
    override var name: String = "Flow Control Action"
    /** Logical action to perform. Default: [FlowControlAction.Action.PAUSE]. */
    var action: FlowControlAction.Action = FlowControlAction.Action.PAUSE
    /**
     * Target threads for Stop / Stop Now.
     * GUI disables this field when action is not Stop or Stop Now, but the value is always serialized.
     * Default: [FlowControlAction.Target.CURRENT_THREAD].
     */
    var target: FlowControlAction.Target = FlowControlAction.Target.CURRENT_THREAD
    /**
     * Pause duration in milliseconds.
     * GUI disables this field when action is not Pause, but the value is always serialized.
     * Default: 0.
     */
    var duration: Long = 0L

    override fun doBuild(): FlowControlAction = FlowControlAction(
        name = name,
        comment = comment,
        action = action,
        target = target,
        duration = duration,
        enabled = enabled
    )
}
