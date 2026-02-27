package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroup

fun ThreadGroup.toJmxNode(): JmxElement {
    val loopController = elementProp(
        "ThreadGroup.main_controller", "LoopController",
        "LoopControlPanel", "LoopController", "Loop Controller",
        listOf(
            stringProp("LoopController.loops", loopCount?.toString() ?: "-1"),
            boolProp("LoopController.continue_forever", loopCount == null)
        )
    )

    val attrs = buildMap {
        put("guiclass", "ThreadGroupGui"); put("testclass", "ThreadGroup"); put("testname", name)
        if (!enabled) put("enabled", "false")
    }

    return JmxElement(
        tag = "ThreadGroup",
        attributes = attrs,
        children = buildList {
            add(intProp("ThreadGroup.num_threads", numberOfThreads))
            add(intProp("ThreadGroup.ramp_time", rampUpPeriodTime))
            duration?.let    { add(longProp("ThreadGroup.duration", it.toLong())) }
            startupDelay?.let { add(longProp("ThreadGroup.delay",    it.toLong())) }
            add(boolProp("ThreadGroup.same_user_on_next_iteration", sameUserOnEachIteration))
            if (specifyThreadLifetime) add(boolProp("ThreadGroup.scheduler", true))
            if (delayThreadCreationUntilNeeded) add(boolProp("ThreadGroup.delayedStart", true))
            add(stringProp("ThreadGroup.on_sample_error", actionToBeTakenAfterSampleError.jmxValue))
            add(loopController)
        }
    )
}
