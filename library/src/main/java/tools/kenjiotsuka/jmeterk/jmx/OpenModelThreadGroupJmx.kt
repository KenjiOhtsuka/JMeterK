package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.thread.OpenModelThreadGroup

fun OpenModelThreadGroup.toJmxNode(): JmxElement {
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
            add(stringProp("ThreadGroup.on_sample_error", actionToBeTakenAfterSampleError.jmxValue))
            add(mainController)
        }
    )
}
