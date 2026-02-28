package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.postprocessor.CssSelectorExtractor

fun CssSelectorExtractor.toJmxNode(): JmxElement = JmxElement(
    tag = "HtmlExtractor",
    attributes = buildMap {
        put("guiclass", "HtmlExtractorGui")
        put("testclass", "HtmlExtractor")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(stringProp("HtmlExtractor.refname", referenceName))
        add(stringProp("HtmlExtractor.expr", cssSelector))
        add(stringProp("HtmlExtractor.attribute", attribute))
        add(stringProp("HtmlExtractor.default", defaultValue))
        add(boolProp("HtmlExtractor.default_empty_value", useEmptyDefaultValue))
        add(stringProp("HtmlExtractor.match_number", matchNo?.toString() ?: ""))
        add(stringProp("HtmlExtractor.extractor_impl", implementation?.jmxValue ?: ""))
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
