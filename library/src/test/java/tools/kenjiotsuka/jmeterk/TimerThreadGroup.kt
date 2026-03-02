package tools.kenjiotsuka.jmeterk

import tools.kenjiotsuka.jmeterk.model.core.TestPlanBuilder

internal fun TestPlanBuilder.timerThreadGroup() {
    threadGroup {
        name = "Timer Thread Group"
        numberOfThreads = 1
        rampUpPeriodTime = 1
        sameUserOnEachIteration = true

        constantTimer { /* all defaults: delay = 300 */ }

        uniformRandomTimer { /* all defaults: randomDelayMaximum = 100.0, constantDelayOffset = 0 */ }

        preciseThroughputTimer { /* all defaults */ }

        constantThroughputTimer { /* all defaults */ }
    }
}
