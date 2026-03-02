package tools.kenjiotsuka.jmeterk

import tools.kenjiotsuka.jmeterk.model.assertion.Jsr223Assertion
import tools.kenjiotsuka.jmeterk.model.assertion.ResponseAssertion
import tools.kenjiotsuka.jmeterk.model.core.TestPlanBuilder
import tools.kenjiotsuka.jmeterk.model.jsr223.Jsr223Language
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest

internal fun TestPlanBuilder.httpRequestThreadGroup() {
    threadGroup {
        name = "Thread Group"
        numberOfThreads = 1
        rampUpPeriodTime = 1
        duration = 1
        startupDelay = 2
        sameUserOnEachIteration = true
        // actionToBeTakenAfterSampleError = CONTINUE (default)
        // loopCount = 1 (default)

        anyElement {
            tagName = "TestAction"
            name = "Flow Control Action !!!"
            attributes = mapOf(
                "guiclass"  to "TestActionGui",
                "testclass" to "TestAction",
                "testname"  to "Flow Control Action !!!"
            )
            configNode { tagName = "intProp"; attributes = mapOf("name" to "ActionProcessor.action"); value = "1" }
            configNode { tagName = "intProp"; attributes = mapOf("name" to "ActionProcessor.target"); value = "0" }
            configNode { tagName = "stringProp"; attributes = mapOf("name" to "ActionProcessor.duration"); value = "0" }
        }

        httpRequest {
            name = "HTTP Request"
            httpRequestMethod = HttpRequest.Method.GET
            bodyData = " "
            // followRedirects = true (default)
            // useKeepAlive = true (default)

            responseAssertion {
                name = "Response Assertion !!!"
                // enabled = true (default)
                fieldToTest = ResponseAssertion.FieldToTest.TEXT_RESPONSE
                patternMatchingRule = ResponseAssertion.PatternMatchingRule.SUBSTRING
                not = false
                or = true   // SUBSTRING(16) + OR(32) = test_type 48
                patterns.addAll(listOf("oo", "ll"))
                customFailureMessage = "aaaa"
            }

            jsr223Assertion {
                name = "JSR223 Assertion !!!"
                comment = "hello"
                language = Jsr223Language.GROOVY
                parameters = "a"
                cacheCompiledScriptIfAvailable = true
                // script = "" (default)
                // filename = "" (default)
            }
        }

        httpRequest {
            name = "HTTP Request"
            enabled = false
            httpRequestMethod = HttpRequest.Method.GET
            // bodyData = "" → parameters mode (default)
        }

        httpHeaderManager {
            name = "HTTP Header Manager"
            enabled = false
            // no headers
        }

        ifController {
            name = "If Controller"
            condition =
                $$"${JMeterThread.last_sample_ok}\n\n${JMeterThread.last_sample_ok}\n\n${JMeterThread.last_sample_ok}"
            evaluateAll = true
            useExpression = true

            whileController {
                name = "While Controller"
                // condition = "" (default, empty)
            }
        }

        loopController {
            name = "Loop Controller"
            loopCount = null // infinite (loops = -1, continue_forever omitted)

            whileController {
                name = "While Controller"
                condition = "1\n\n2\n\n3"
            }
        }
    }
}
