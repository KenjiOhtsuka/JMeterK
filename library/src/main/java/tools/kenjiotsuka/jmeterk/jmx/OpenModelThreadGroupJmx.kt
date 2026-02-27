package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.thread.ActionToBeTakenAfterSampleError
import tools.kenjiotsuka.jmeterk.model.thread.OpenModelThreadGroup

fun OpenModelThreadGroup.toJmxNode(): JmxElement {
    val onError = when (actionToBeTakenAfterSampleError) {
        ActionToBeTakenAfterSampleError.CONTINUE               -> "continue"
        ActionToBeTakenAfterSampleError.START_NEXT_THREAD_LOOP -> "startnextloop"
        ActionToBeTakenAfterSampleError.STOP_THREAD            -> "stopthread"
        ActionToBeTakenAfterSampleError.STOP_TEST              -> "stoptest"
        ActionToBeTakenAfterSampleError.STOP_TEST_NOW          -> "stoptestnow"
    }

    // Always present, self-closing (no children)
    val mainController = elementProp(
        "ThreadGroup.main_controller", "OpenModelThreadGroupController",
        emptyList()
    )

    return JmxElement(
        tag = "OpenModelThreadGroup",
        attributes = buildMap {
            put("guiclass", "OpenModelThreadGroupGui")
            put("testclass", "OpenModelThreadGroup")
            put("testname", name)
            if (!enabled) put("enabled", "false")
        },
        children = buildList {
            if (schedule.isNotEmpty()) add(stringProp("OpenModelThreadGroup.schedule", schedule))
            if (randomSeed != 0L) add(longProp("OpenModelThreadGroup.random_seed", randomSeed))
            add(stringProp("ThreadGroup.on_sample_error", onError))
            add(mainController)
        }
    )
}
