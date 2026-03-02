package tools.kenjiotsuka.jmeterk

import tools.kenjiotsuka.jmeterk.model.core.TestPlanBuilder

internal fun TestPlanBuilder.preProcessorThreadGroup() {
    threadGroup {
        name = "PreProcessor Thread Group"
        numberOfThreads = 1
        rampUpPeriodTime = 1
        sameUserOnEachIteration = true

        userParameters {
            name = "User Parameters"
            comment = "User Parameters Comment"
            // perIteration = false (default)
            variable("u", "t", "")
            variable("t", "", "g")
        }
    }
}
