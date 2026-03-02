package tools.kenjiotsuka.jmeterk

import tools.kenjiotsuka.jmeterk.model.core.TestPlanBuilder
import tools.kenjiotsuka.jmeterk.model.thread.ActionToBeTakenAfterSampleError

internal fun TestPlanBuilder.openModelThreadGroupSetup() {
    openModelThreadGroup {
        name = "Open Model Thread Group"
        schedule = "rate(1/min) random_arrivals(10 min) /* comment */"
        randomSeed = 5678L
        actionToBeTakenAfterSampleError = ActionToBeTakenAfterSampleError.STOP_TEST_NOW

        jsr223PreProcessor {
            name = "JSR223 PreProcessor"
            // language = GROOVY (default)
            // all other fields default (empty/true)
        }

        jsr223Sampler {
            name = "JSR223 Sampler"
            // language = GROOVY (default)
        }

        jsr223PostProcessor {
            name = "JSR223 PostProcessor"
            // language = GROOVY (default)
        }
    }
}
