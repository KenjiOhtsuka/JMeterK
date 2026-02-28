package tools.kenjiotsuka.jmeterk.model.postprocessor

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class CssSelectorExtractor(
    override val name: String,
    override val comment: String,
    /** GUI: "Apply to" radio buttons. Default: [ApplyTo.MAIN_SAMPLE_ONLY]. */
    val applyTo: ApplyTo,
    /**
     * JMeter variable name used when [applyTo] is [ApplyTo.JMETER_VARIABLE].
     * JMX: `Scope.variable`.
     */
    val jmeterVariableName: String,
    /** GUI: "CSS Selector Extractor Implementation" dropdown. Default: `null` (JMeter auto-selects). */
    val implementation: Implementation?,
    /** GUI: "Name of created variable". JMX: `HtmlExtractor.refname`. */
    val referenceName: String,
    /** GUI: "CSS Selector expressions". JMX: `HtmlExtractor.expr`. */
    val cssSelector: String,
    /** GUI: "Attribute". JMX: `HtmlExtractor.attribute`. */
    val attribute: String,
    /** GUI: "Match No. (0 for Random)". JMX: `HtmlExtractor.match_number`. `null` = not set (empty string). Default: null. */
    val matchNo: Int?,
    /**
     * GUI: "Default Value". JMX: `HtmlExtractor.default`.
     * Ignored (not emitted) when [useEmptyDefaultValue] is true.
     */
    val defaultValue: String,
    /** GUI: "Use empty default value" checkbox. JMX: `HtmlExtractor.default_empty_value`. Default: false. */
    val useEmptyDefaultValue: Boolean,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled) {

    /**
     * GUI: "CSS Selector Extractor Implementation" dropdown.
     * Controls which CSS/JQuery parsing library JMeter uses.
     */
    enum class Implementation(val jmxValue: String) {
        /** JSOUP library. JMX: `"JSOUP"`. */
        JSOUP("JSOUP"),
        /** JODD library. JMX: `"JODD"`. */
        JODD("JODD")
    }
}

class CssSelectorExtractorBuilder : JMeterLeafBuilder<CssSelectorExtractor>() {
    override var name: String = "CSS Selector Extractor"
    var applyTo: ApplyTo = ApplyTo.MAIN_SAMPLE_ONLY
    var jmeterVariableName: String = ""
    var implementation: CssSelectorExtractor.Implementation? = null
    var referenceName: String = ""
    var cssSelector: String = ""
    var attribute: String = ""
    var matchNo: Int? = null
    var defaultValue: String = ""
    var useEmptyDefaultValue: Boolean = false

    override fun doBuild(): CssSelectorExtractor = CssSelectorExtractor(
        name = name,
        comment = comment,
        applyTo = applyTo,
        jmeterVariableName = jmeterVariableName,
        implementation = implementation,
        referenceName = referenceName,
        cssSelector = cssSelector,
        attribute = attribute,
        matchNo = matchNo,
        defaultValue = defaultValue,
        useEmptyDefaultValue = useEmptyDefaultValue,
        enabled = enabled
    )
}
