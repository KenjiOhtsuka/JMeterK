package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.postprocessor.JsonExtractor

fun JsonExtractor.toJmxNode(): JmxElement = JmxElement(
    tag = "JSONPostProcessor",
    attributes = buildMap {
        put("guiclass", "JSONPostProcessorGui")
        put("testclass", "JSONPostProcessor")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(stringProp("JSONPostProcessor.referenceNames", referenceName))
        add(stringProp("JSONPostProcessor.jsonPathExprs", jsonPathExpression))
        add(stringProp("JSONPostProcessor.match_numbers", matchNo?.toString() ?: ""))
        if (defaultValue.isNotEmpty()) add(stringProp("JSONPostProcessor.defaultValues", defaultValue))
        if (computeConcatenation) add(boolProp("JSONPostProcessor.compute_concat", true))
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
    }
)
