package tools.kenjiotsuka.jmeterk

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.kenjiotsuka.jmeterk.jmx.buildJmxDocument
import tools.kenjiotsuka.jmeterk.model.assertion.Jsr223Assertion
import tools.kenjiotsuka.jmeterk.model.assertion.ResponseAssertion
import tools.kenjiotsuka.jmeterk.model.configelement.HttpHeaderManagerBuilder
import tools.kenjiotsuka.jmeterk.model.core.ConfigNode
import tools.kenjiotsuka.jmeterk.model.core.testPlan
import tools.kenjiotsuka.jmeterk.model.thread.ActionToBeTakenAfterSampleError
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest

class TestPlanSerializationTest {

    /**
     * Builds the same structure as testfile/test.jmx and verifies the serialized output matches.
     *
     * Normalization: strips newline-based indentation whitespace between tags while preserving
     * meaningful text content (e.g. the single space in Argument.value).
     */
    @Test
    fun `serialized DSL output matches test jmx`() {
        val plan = testPlan {
            name = "Test Plan"
            // functionalMode = false → omitted (default)
            // serializeThreadGroups = false → omitted (default)

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
                    configNode {
                        tagName = "intProp"
                        attributes = mapOf("name" to "ActionProcessor.action")
                        value = "1"
                    }
                    configNode {
                        tagName = "intProp"
                        attributes = mapOf("name" to "ActionProcessor.target")
                        value = "0"
                    }
                    configNode {
                        tagName = "stringProp"
                        attributes = mapOf("name" to "ActionProcessor.duration")
                        value = "0"
                    }
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
                        matchingRule = ResponseAssertion.PatternMatchingRule.SUBSTRING
                        not = false
                        or = true   // SUBSTRING(16) + OR(32) = test_type 48
                        patterns.addAll(listOf("oo", "ll"))
                        customFailureMessage = "aaaa"
                    }

                    jsr223Assertion {
                        name = "JSR223 Assertion !!!"
                        comment = "hello"
                        language = Jsr223Assertion.Language.GROOVY
                        parameters = "a"
                        cacheCompiledScriptIfAvailable = true
                        // script = "" (default)
                        // filename = "" (default)
                    }
                }

                httpHeaderManager {
                    name = "HTTP Header Manager"
                    header("header1", "value1")
                    header("header2", "value2")
                    header("header1", "value3")
                }

                httpRequest {
                    name = "HTTP Request"
                    enabled = false
                    httpRequestMethod = HttpRequest.Method.GET
                    // bodyData = "" → parameters mode (default)
                    // followRedirects = true (default)
                    // useKeepAlive = true (default)
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
                }
            }

            openModelThreadGroup {
                name = "Open Model Thread Group"
                schedule = "rate(1/min) random_arrivals(10 min) /* comment */"
                randomSeed = 5678L
                actionToBeTakenAfterSampleError = ActionToBeTakenAfterSampleError.STOP_TEST_NOW
            }
        }

        val actual = normalizeXml(buildJmxDocument(plan))
        val expected = normalizeXml(loadResource("test.jmx"))

        assertEquals(expected, actual)
    }

    private fun loadResource(name: String): String =
        javaClass.classLoader.getResourceAsStream(name)!!.bufferedReader().readText()

    /**
     * Strips newline-based indentation (newline + optional spaces/tabs between tags) so that
     * compact and indented XML representations can be compared structurally.
     * Single spaces inside text nodes (e.g. Argument.value = " ") are preserved.
     */
    private fun normalizeXml(xml: String): String =
        xml.replace(Regex(">[\r\n][ \t]*<"), "><")
           .replace(Regex("""\s+enabled="true""""), "")
           .trim()
}
