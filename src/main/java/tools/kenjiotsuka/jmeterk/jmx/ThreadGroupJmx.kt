package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroup

fun ThreadGroup.toJmxNode(): JmxElement {
    val onError = when (actionToBeTakenAfterSampleError) {
        ThreadGroup.ActionToBeTakenAfterSampleError.Continue           -> "continue"
        ThreadGroup.ActionToBeTakenAfterSampleError.StartNextThreadLoop -> "startnextloop"
        ThreadGroup.ActionToBeTakenAfterSampleError.StopThread          -> "stopthread"
        ThreadGroup.ActionToBeTakenAfterSampleError.StopTest            -> "stoptest"
        ThreadGroup.ActionToBeTakenAfterSampleError.StopTestNow         -> "stoptestnow"
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
