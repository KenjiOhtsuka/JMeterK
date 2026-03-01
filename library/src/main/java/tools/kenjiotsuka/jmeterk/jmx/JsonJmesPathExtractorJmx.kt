package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.postprocessor.JsonJmesPathExtractor

fun JsonJmesPathExtractor.toJmxNode(): JmxElement = JmxElement(
    tag = "JMESPathExtractor",
    attributes = buildMap {
        put("guiclass", "JMESPathExtractorGui")
        put("testclass", "JMESPathExtractor")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(stringProp("JMESExtractor.referenceName", referenceName))
        add(stringProp("JMESExtractor.jmesPathExpr", jmesPathExpression))
        add(stringProp("JMESExtractor.matchNumber", matchNo?.toString() ?: ""))
        if (defaultValue.isNotEmpty()) add(stringProp("JMESExtractor.defaultValues", defaultValue))
        if (computeConcatenation) add(boolProp("JMESExtractor.compute_concat", true))
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
