package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroup

fun ThreadGroup.toJmxNode(): JmxElement {
    val onError = when (actionToBeTakenAfterSampleError) {
        ThreadGroup.ActionToBeTakenAfterSampleError.CONTINUE              -> "continue"
        ThreadGroup.ActionToBeTakenAfterSampleError.START_NEXT_THREAD_LOOP -> "startnextloop"
        ThreadGroup.ActionToBeTakenAfterSampleError.STOP_THREAD            -> "stopthread"
        ThreadGroup.ActionToBeTakenAfterSampleError.STOP_TEST              -> "stoptest"
        ThreadGroup.ActionToBeTakenAfterSampleError.STOP_TEST_NOW          -> "stoptestnow"
    }

    val loopController = elementProp(
        "ThreadGroup.main_controller",
        "LoopController",
        "LoopControlPanel",
        "LoopController",
        "Loop Controller",
        enabled,
        listOf(
            boolProp("LoopController.continue_forever", loopCount == null),
            stringProp("LoopController.loops", loopCount?.toString() ?: "-1")
        )
    )

    return JmxElement(
        tag = "ThreadGroup",
        attributes = mapOf(
            "guiclass"  to "ThreadGroupGui",
            "testclass" to "ThreadGroup",
            "testname"  to name,
            "enabled"   to enabled.toString()
        ),
        children = buildList {
            add(stringProp("ThreadGroup.num_threads", numberOfThreads.toString()))
            add(stringProp("ThreadGroup.ramp_time", rampUpPeriodTime.toString()))
            add(stringProp("ThreadGroup.on_sample_error", onError))
            add(loopController)
            add(boolProp("ThreadGroup.same_user_on_next_iteration", sameUserOnEachIteration))
            add(boolProp("ThreadGroup.delayedStart", delayThreadCreationUntilNeeded))
            add(boolProp("ThreadGroup.scheduler", specifyThreadLifetime))
            add(stringProp("ThreadGroup.duration", duration?.toString() ?: ""))
            add(stringProp("ThreadGroup.delay", startupDelay?.toString() ?: ""))
            addAll(children.map { it.toJmxNode() })
        }
    )
}
