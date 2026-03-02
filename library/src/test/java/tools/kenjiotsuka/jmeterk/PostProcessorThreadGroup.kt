package tools.kenjiotsuka.jmeterk

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.core.TestPlanBuilder
import tools.kenjiotsuka.jmeterk.model.postprocessor.CssSelectorExtractor

internal fun TestPlanBuilder.postProcessorThreadGroup() {
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

        jsonJmesPathExtractor {
            // all fields default (name = "JSON JMESPath Extractor", applyTo = MAIN_SAMPLE_ONLY)
        }

        boundaryExtractor {
            referenceName = "name of created variable"
            leftBoundary = "left boundary"
            useEmptyDefaultValue = true
            matchNo = 3
            applyTo = ApplyTo.JMETER_VARIABLE
            comment = "comment"
        }

        boundaryExtractor {
            // all fields default (name = "Boundary Extractor", fieldToCheck = BODY, applyTo = MAIN_SAMPLE_ONLY)
        }

        anyElement {
            tagName = "RegexExtractor"
            attributes = mapOf(
                "guiclass" to "RegexExtractorGui",
                "testclass" to "RegexExtractor",
                "testname" to "Regular Expression Extractor"
            )
            configNode { tagName = "stringProp"; attributes = mapOf("name" to "RegexExtractor.useHeaders"); value = "false" }
            configNode { tagName = "stringProp"; attributes = mapOf("name" to "RegexExtractor.refname"); value = "" }
            configNode { tagName = "stringProp"; attributes = mapOf("name" to "RegexExtractor.regex"); value = "" }
            configNode { tagName = "stringProp"; attributes = mapOf("name" to "RegexExtractor.template"); value = "" }
            configNode { tagName = "stringProp"; attributes = mapOf("name" to "RegexExtractor.default"); value = "" }
            configNode { tagName = "boolProp"; attributes = mapOf("name" to "RegexExtractor.default_empty_value"); value = "false" }
            configNode { tagName = "stringProp"; attributes = mapOf("name" to "RegexExtractor.match_number"); value = "" }
        }
    }
}
