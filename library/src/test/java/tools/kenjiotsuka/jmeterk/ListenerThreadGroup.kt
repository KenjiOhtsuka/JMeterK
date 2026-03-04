package tools.kenjiotsuka.jmeterk

import tools.kenjiotsuka.jmeterk.model.core.ConfigNodeBuilder
import tools.kenjiotsuka.jmeterk.model.core.TestPlanBuilder

internal fun TestPlanBuilder.listenerThreadGroup() {
    threadGroup {
        name = "Listener Thread Group"
        numberOfThreads = 1
        rampUpPeriodTime = 1
        sameUserOnEachIteration = true

        anyElement {
            tagName = "ResultCollector"
            attributes = mapOf(
                "guiclass" to "ViewResultsFullVisualizer",
                "testclass" to "ResultCollector",
                "testname" to "View Results Tree"
            )
            configNode { tagName = "boolProp"; attributes = mapOf("name" to "ResultCollector.error_logging"); value = "false" }
            resultCollectorSaveConfig()
            configNode { tagName = "stringProp"; attributes = mapOf("name" to "filename"); value = "" }
        }

        anyElement {
            tagName = "ResultCollector"
            attributes = mapOf(
                "guiclass" to "SummaryReport",
                "testclass" to "ResultCollector",
                "testname" to "Summary Report"
            )
            configNode { tagName = "boolProp"; attributes = mapOf("name" to "ResultCollector.error_logging"); value = "false" }
            resultCollectorSaveConfig()
            configNode { tagName = "stringProp"; attributes = mapOf("name" to "filename"); value = "" }
        }

        anyElement {
            tagName = "ResultCollector"
            attributes = mapOf(
                "guiclass" to "StatVisualizer",
                "testclass" to "ResultCollector",
                "testname" to "Aggregate Report"
            )
            configNode { tagName = "boolProp"; attributes = mapOf("name" to "ResultCollector.error_logging"); value = "false" }
            resultCollectorSaveConfig()
            configNode { tagName = "stringProp"; attributes = mapOf("name" to "filename"); value = "" }
        }

        anyElement {
            tagName = "BackendListener"
            attributes = mapOf(
                "guiclass" to "BackendListenerGui",
                "testclass" to "BackendListener",
                "testname" to "Backend Listener"
            )
            configNode {
                tagName = "elementProp"
                attributes = mapOf(
                    "name" to "arguments",
                    "elementType" to "Arguments",
                    "guiclass" to "ArgumentsPanel",
                    "testclass" to "Arguments"
                )
                configNode {
                    tagName = "collectionProp"
                    attributes = mapOf("name" to "Arguments.arguments")
                    backendArg("graphiteMetricsSender", "org.apache.jmeter.visualizers.backend.graphite.TextGraphiteMetricsSender")
                    backendArg("graphiteHost", "")
                    backendArg("graphitePort", "2003")
                    backendArg("rootMetricsPrefix", "jmeter.")
                    backendArg("summaryOnly", "true")
                    backendArg("samplersList", "")
                    backendArg("percentiles", "90;95;99")
                }
            }
            configNode {
                tagName = "stringProp"
                attributes = mapOf("name" to "classname")
                value = "org.apache.jmeter.visualizers.backend.graphite.GraphiteBackendListenerClient"
            }
        }
    }
}

internal fun TestPlanBuilder.samplerThreadGroup() {
    threadGroup {
        name = "Sampler Thread Group"
        numberOfThreads = 1
        rampUpPeriodTime = 1
        sameUserOnEachIteration = true
    }
}

private fun tools.kenjiotsuka.jmeterk.model.core.AnyElementBuilder.resultCollectorSaveConfig() {
    configNode {
        tagName = "objProp"
        configNode { tagName = "name"; value = "saveConfig" }
        configNode {
            tagName = "value"
            attributes = mapOf("class" to "SampleSaveConfiguration")
            configNode { tagName = "time"; value = "true" }
            configNode { tagName = "latency"; value = "true" }
            configNode { tagName = "timestamp"; value = "true" }
            configNode { tagName = "success"; value = "true" }
            configNode { tagName = "label"; value = "true" }
            configNode { tagName = "code"; value = "true" }
            configNode { tagName = "message"; value = "true" }
            configNode { tagName = "threadName"; value = "true" }
            configNode { tagName = "dataType"; value = "true" }
            configNode { tagName = "encoding"; value = "false" }
            configNode { tagName = "assertions"; value = "true" }
            configNode { tagName = "subresults"; value = "true" }
            configNode { tagName = "responseData"; value = "false" }
            configNode { tagName = "samplerData"; value = "false" }
            configNode { tagName = "xml"; value = "false" }
            configNode { tagName = "fieldNames"; value = "true" }
            configNode { tagName = "responseHeaders"; value = "false" }
            configNode { tagName = "requestHeaders"; value = "false" }
            configNode { tagName = "responseDataOnError"; value = "false" }
            configNode { tagName = "saveAssertionResultsFailureMessage"; value = "true" }
            configNode { tagName = "assertionsResultsToSave"; value = "0" }
            configNode { tagName = "bytes"; value = "true" }
            configNode { tagName = "sentBytes"; value = "true" }
            configNode { tagName = "url"; value = "true" }
            configNode { tagName = "threadCounts"; value = "true" }
            configNode { tagName = "idleTime"; value = "true" }
            configNode { tagName = "connectTime"; value = "true" }
        }
    }
}

private fun ConfigNodeBuilder.backendArg(name: String, value: String) {
    configNode {
        tagName = "elementProp"
        attributes = mapOf("name" to name, "elementType" to "Argument")
        configNode { tagName = "stringProp"; attributes = mapOf("name" to "Argument.name"); this.value = name }
        configNode { tagName = "stringProp"; attributes = mapOf("name" to "Argument.value"); this.value = value }
        configNode { tagName = "stringProp"; attributes = mapOf("name" to "Argument.metadata"); this.value = "=" }
    }
}
