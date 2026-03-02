package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.postprocessor.BoundaryExtractor

fun BoundaryExtractor.toJmxNode(): JmxElement = JmxElement(
    tag = "BoundaryExtractor",
    attributes = buildMap {
        put("guiclass", "BoundaryExtractorGui")
        put("testclass", "BoundaryExtractor")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        add(stringProp("BoundaryExtractor.useHeaders", fieldToCheck.jmxValue))
        add(stringProp("BoundaryExtractor.refname", referenceName))
        add(stringProp("BoundaryExtractor.lboundary", leftBoundary))
        add(stringProp("BoundaryExtractor.rboundary", rightBoundary))
        add(stringProp("BoundaryExtractor.default", defaultValue))
        add(boolProp("BoundaryExtractor.default_empty_value", useEmptyDefaultValue))
        add(stringProp("BoundaryExtractor.match_number", matchNo?.toString() ?: ""))
        when (applyTo) {
            ApplyTo.MAIN_SAMPLE_AND_SUB_SAMPLES ->
                add(stringProp("Sample.scope", "all"))
            ApplyTo.SUB_SAMPLES_ONLY ->
                add(stringProp("Sample.scope", "children"))
            ApplyTo.JMETER_VARIABLE -> {
                add(stringProp("Sample.scope", "variable"))
                add(stringProp("Scope.variable", jmeterVariableName))
            }
            ApplyTo.MAIN_SAMPLE_ONLY -> { /* default — Sample.scope not emitted */ }
        }
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
    }
)
