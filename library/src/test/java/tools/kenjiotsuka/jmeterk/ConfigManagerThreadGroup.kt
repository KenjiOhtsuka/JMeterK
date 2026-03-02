package tools.kenjiotsuka.jmeterk

import tools.kenjiotsuka.jmeterk.model.core.TestPlanBuilder

internal fun TestPlanBuilder.configManagerThreadGroup() {
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
}
