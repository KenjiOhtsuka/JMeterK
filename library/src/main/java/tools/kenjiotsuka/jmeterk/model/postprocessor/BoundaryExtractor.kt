package tools.kenjiotsuka.jmeterk.model.postprocessor

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class BoundaryExtractor(
    override val name: String,
    override val comment: String,
    /** GUI: "Apply to" radio buttons. Default: [ApplyTo.MAIN_SAMPLE_ONLY]. */
    val applyTo: ApplyTo,
    /**
     * JMeter variable name used when [applyTo] is [ApplyTo.JMETER_VARIABLE].
     * JMX: `Scope.variable`.
     */
    val jmeterVariableName: String,
    /** GUI: "Field to check" radio buttons. Default: [FieldToCheck.BODY]. JMX: `BoundaryExtractor.useHeaders`. */
    val fieldToCheck: FieldToCheck,
    /** GUI: "Name of created variable". JMX: `BoundaryExtractor.refname`. */
    val referenceName: String,
    /** GUI: "Left boundary". JMX: `BoundaryExtractor.lboundary`. */
    val leftBoundary: String,
    /** GUI: "Right boundary". JMX: `BoundaryExtractor.rboundary`. */
    val rightBoundary: String,
    /** GUI: "Match No. (0 for Random)". JMX: `BoundaryExtractor.match_number`. `null` = not set (empty string). */
    val matchNo: Int?,
    /**
     * GUI: "Default Value". JMX: `BoundaryExtractor.default`.
     * Always emitted (even when [useEmptyDefaultValue] is true).
     */
    val defaultValue: String,
    /** GUI: "Use empty default value" checkbox. JMX: `BoundaryExtractor.default_empty_value`. Default: false. */
    val useEmptyDefaultValue: Boolean,
    override val enabled: Boolean,
) : JMeterLeaf(name, comment, enabled) {

    /**
     * GUI: "Field to check" radio button group.
     * Controls which part of the HTTP response is searched.
     * JMX: `BoundaryExtractor.useHeaders` (stringProp).
     */
    enum class FieldToCheck(val jmxValue: String) {
        /** GUI: "Body". JMX: `"false"`. */
        BODY("false"),
        /** GUI: "Body (unescaped)". JMX: `"unescaped"`. */
        BODY_UNESCAPED("unescaped"),
        /** GUI: "Body as a Document". JMX: `"as_document"`. */
        BODY_AS_DOCUMENT("as_document"),
        /** GUI: "Response Headers". JMX: `"true"`. */
        RESPONSE_HEADERS("true"),
        /** GUI: "Request Headers". JMX: `"request_headers"`. */
        REQUEST_HEADERS("request_headers"),
        /** GUI: "URL". JMX: `"URL"`. */
        URL("URL"),
        /** GUI: "Response Code". JMX: `"code"`. */
        RESPONSE_CODE("code"),
        /** GUI: "Response Message". JMX: `"message"`. */
        RESPONSE_MESSAGE("message"),
    }
}

class BoundaryExtractorBuilder : JMeterLeafBuilder<BoundaryExtractor>() {
    override var name: String = "Boundary Extractor"
    var applyTo: ApplyTo = ApplyTo.MAIN_SAMPLE_ONLY
    var jmeterVariableName: String = ""
    var fieldToCheck: BoundaryExtractor.FieldToCheck = BoundaryExtractor.FieldToCheck.BODY
    var referenceName: String = ""
    var leftBoundary: String = ""
    var rightBoundary: String = ""
    var matchNo: Int? = null
    var defaultValue: String = ""
    var useEmptyDefaultValue: Boolean = false

    override fun doBuild(): BoundaryExtractor = BoundaryExtractor(
        name = name,
        comment = comment,
        applyTo = applyTo,
        jmeterVariableName = jmeterVariableName,
        fieldToCheck = fieldToCheck,
        referenceName = referenceName,
        leftBoundary = leftBoundary,
        rightBoundary = rightBoundary,
        matchNo = matchNo,
        defaultValue = defaultValue,
        useEmptyDefaultValue = useEmptyDefaultValue,
        enabled = enabled,
    )
}
