package tools.kenjiotsuka.jmeterk

import tools.kenjiotsuka.jmeterk.model.core.TestPlanBuilder
import tools.kenjiotsuka.jmeterk.model.sampler.FlowControlAction

internal fun TestPlanBuilder.debugSamplerThreadGroup() {
    threadGroup {
        name = "Thread Group"
        numberOfThreads = 1
        rampUpPeriodTime = 1
        sameUserOnEachIteration = true
        // duration = null → not emitted
        // startupDelay = null → not emitted

        debugSampler {
            name = "Debug Sampler"
            displayJMeterVariables = true

            jsonAssertion {
                name = "JSON Assertion"
                // jsonPath = "" (default)
                expectedValue = "ｆ\n\nｆｒ"
                // additionallyAssertValue = false (default)
                expectNull = true
                // invertAssertion = false (default)
                // matchAsRegularExpression = true (default)
            }

            sizeAssertion {
                name = "Size Assertion"
                // responseField = FULL_RESPONSE (default)
                // size = null → empty string (default)
                // comparisonOperator = EQUAL (default)
            }

            xpath2Assertion {
                name = "XPath2 Assertion"
                xPath = "/aaaaerriivmo///"
                // invertAssertion = false (default)
                // namespacesAliasesList = "" (default)
                // applyTo = MAIN_SAMPLE_ONLY (default)
            }
        }

        transactionController {
            name = "Transaction Controller"
            // generateParentSample = false → not emitted (default)
            // includeTimers = false (default, always emitted)
        }

        flowControlAction {
            name = "Flow Control Action"
            action = FlowControlAction.Action.GO_TO_NEXT_ITERATION_OF_CURRENT_LOOP
            // target = CURRENT_THREAD (default)
            // duration = 0 (default)
        }
    }
}
