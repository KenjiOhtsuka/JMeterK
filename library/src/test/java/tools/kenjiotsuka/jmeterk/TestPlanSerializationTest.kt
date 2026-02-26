package tools.kenjiotsuka.jmeterk

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.kenjiotsuka.jmeterk.jmx.buildJmxDocument
import tools.kenjiotsuka.jmeterk.model.assertion.Jsr223Assertion
import tools.kenjiotsuka.jmeterk.model.assertion.ResponseAssertion
import tools.kenjiotsuka.jmeterk.model.core.ConfigNode
import tools.kenjiotsuka.jmeterk.model.core.testPlan
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
            // functionalMode = false (default)
            // serializeThreadGroups = false (default)

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
                        enabled = false
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
            }

            threadGroup {
                name = "Thread Group"
                numberOfThreads = 1
                rampUpPeriodTime = 1
                sameUserOnEachIteration = true
                // duration = null → not emitted
                // startupDelay = null → not emitted
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
        xml.replace(Regex(">[\r\n][ \t]*<"), "><").trim()
}
