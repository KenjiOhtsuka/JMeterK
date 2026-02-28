package tools.kenjiotsuka.jmeterk

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.kenjiotsuka.jmeterk.jmx.buildJmxDocument
import tools.kenjiotsuka.jmeterk.model.assertion.Jsr223Assertion
import tools.kenjiotsuka.jmeterk.model.assertion.ResponseAssertion
import tools.kenjiotsuka.jmeterk.model.configelement.HttpHeaderManagerBuilder
import tools.kenjiotsuka.jmeterk.model.core.ConfigNode
import tools.kenjiotsuka.jmeterk.model.core.testPlan
import tools.kenjiotsuka.jmeterk.model.jsr223.Jsr223Language
import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.postprocessor.CssSelectorExtractor
import tools.kenjiotsuka.jmeterk.model.preprocessor.UserParametersBuilder
import tools.kenjiotsuka.jmeterk.model.sampler.FlowControlAction
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest
import tools.kenjiotsuka.jmeterk.model.thread.ActionToBeTakenAfterSampleError

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

            threadGroup {
                name = "Config Manager Thread Group"
                comment = "This is a thread group for testing manager"
                numberOfThreads = 1
                rampUpPeriodTime = 1
                sameUserOnEachIteration = true

                anyElement {
                    tagName = "CSVDataSet"
                    attributes = mapOf(
                        "guiclass" to "TestBeanGUI",
                        "testclass" to "CSVDataSet",
                        "testname" to "CSV Data Set Config"
                    )
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "delimiter"); value = "," }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "fileEncoding"); value = "" }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "filename"); value = "" }
                    configNode { tagName = "boolProp"; attributes = mapOf("name" to "ignoreFirstLine"); value = "false" }
                    configNode { tagName = "boolProp"; attributes = mapOf("name" to "quotedData"); value = "false" }
                    configNode { tagName = "boolProp"; attributes = mapOf("name" to "recycle"); value = "true" }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "shareMode"); value = "shareMode.all" }
                    configNode { tagName = "boolProp"; attributes = mapOf("name" to "stopThread"); value = "false" }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "variableNames"); value = "" }
                }

                httpHeaderManager {
                    name = "HTTP Header Manager"
                    header("header1", "value1")
                    header("header2", "value2")
                    header("header1", "value3")
                }

                anyElement {
                    tagName = "CookieManager"
                    attributes = mapOf(
                        "guiclass" to "CookiePanel",
                        "testclass" to "CookieManager",
                        "testname" to "HTTP Cookie Manager"
                    )
                    configNode { tagName = "collectionProp"; attributes = mapOf("name" to "CookieManager.cookies") }
                    configNode { tagName = "boolProp"; attributes = mapOf("name" to "CookieManager.clearEachIteration"); value = "false" }
                    configNode { tagName = "boolProp"; attributes = mapOf("name" to "CookieManager.controlledByThreadGroup"); value = "false" }
                }

                anyElement {
                    tagName = "CacheManager"
                    attributes = mapOf(
                        "guiclass" to "CacheManagerGui",
                        "testclass" to "CacheManager",
                        "testname" to "HTTP Cache Manager"
                    )
                    configNode { tagName = "boolProp"; attributes = mapOf("name" to "clearEachIteration"); value = "false" }
                    configNode { tagName = "boolProp"; attributes = mapOf("name" to "useExpires"); value = "true" }
                    configNode { tagName = "boolProp"; attributes = mapOf("name" to "CacheManager.controlledByThread"); value = "false" }
                }

                anyElement {
                    tagName = "ConfigTestElement"
                    attributes = mapOf(
                        "guiclass" to "HttpDefaultsGui",
                        "testclass" to "ConfigTestElement",
                        "testname" to "HTTP Request Defaults"
                    )
                    configNode {
                        tagName = "elementProp"
                        attributes = mapOf(
                            "name" to "HTTPsampler.Arguments",
                            "elementType" to "Arguments",
                            "guiclass" to "HTTPArgumentsPanel",
                            "testclass" to "Arguments",
                            "testname" to "User Defined Variables"
                        )
                        configNode { tagName = "collectionProp"; attributes = mapOf("name" to "Arguments.arguments") }
                    }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "HTTPSampler.implementation"); value = "HttpClient4" }
                }
            }

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

            threadGroup {
                name = "PostProcessor Thread Group"
                numberOfThreads = 1
                rampUpPeriodTime = 1
                sameUserOnEachIteration = true

                cssSelectorExtractor {
                    applyTo = ApplyTo.MAIN_SAMPLE_AND_SUB_SAMPLES
                }

                cssSelectorExtractor {
                    referenceName = "name_of_created_variable"
                    cssSelector = ".css_selector_expression"
                    attribute = "attribute"
                    useEmptyDefaultValue = true
                    matchNo = -1
                    implementation = CssSelectorExtractor.Implementation.JODD
                    applyTo = ApplyTo.SUB_SAMPLES_ONLY
                }

                jsonExtractor {
                    // all fields default (name = "JSON Extractor", applyTo = MAIN_SAMPLE_ONLY)
                }

                anyElement {
                    tagName = "JMESPathExtractor"
                    attributes = mapOf(
                        "guiclass" to "JMESPathExtractorGui",
                        "testclass" to "JMESPathExtractor",
                        "testname" to "JSON JMESPath Extractor"
                    )
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "JMESExtractor.referenceName"); value = "" }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "JMESExtractor.jmesPathExpr"); value = "" }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "JMESExtractor.matchNumber"); value = "" }
                }

                anyElement {
                    tagName = "BoundaryExtractor"
                    attributes = mapOf(
                        "guiclass" to "BoundaryExtractorGui",
                        "testclass" to "BoundaryExtractor",
                        "testname" to "Boundary Extractor"
                    )
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "BoundaryExtractor.useHeaders"); value = "false" }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "BoundaryExtractor.refname"); value = "" }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "BoundaryExtractor.lboundary"); value = "" }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "BoundaryExtractor.rboundary"); value = "" }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "BoundaryExtractor.default"); value = "" }
                    configNode { tagName = "boolProp"; attributes = mapOf("name" to "BoundaryExtractor.default_empty_value"); value = "false" }
                    configNode { tagName = "stringProp"; attributes = mapOf("name" to "BoundaryExtractor.match_number"); value = "" }
                }
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
           // Strip numeric name attributes (JMeter-internal hashCode IDs on collectionProp/stringProp)
           .replace(Regex(""" name="-?\d+""""), "")
           .trim()
}
