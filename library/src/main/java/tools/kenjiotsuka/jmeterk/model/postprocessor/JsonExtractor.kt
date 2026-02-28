package tools.kenjiotsuka.jmeterk.model.postprocessor

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class JsonExtractor(
    override val name: String,
    override val comment: String,
    /** GUI: "Apply to" radio buttons. Default: [ApplyTo.MAIN_SAMPLE_ONLY]. */
    val applyTo: ApplyTo,
    /**
     * JMeter variable name used when [applyTo] is [ApplyTo.JMETER_VARIABLE].
     * JMX: `Scope.variable`.
     */
    val jmeterVariableName: String,
    /** GUI: "Names of created variable". JMX: `JSONPostProcessor.referenceNames`. */
    val referenceName: String,
    /** GUI: "JSON Path expression". JMX: `JSONPostProcessor.jsonPathExprs`. */
    val jsonPathExpression: String,
    /** GUI: "Match No. (0 for Random)". JMX: `JSONPostProcessor.match_numbers`. `null` = not set (empty string). */
    val matchNo: Int?,
    /** GUI: "Compute concatenation var (suffix _ALL)" checkbox. JMX: `JSONPostProcessor.compute_concat`. Default: false. */
    val computeConcatenation: Boolean,
    /**
     * GUI: "Default Values". JMX: `JSONPostProcessor.defaultValues`.
     * Only emitted when non-empty.
     */
    val defaultValue: String,
    override val enabled: Boolean,
) : JMeterLeaf(name, comment, enabled)

class JsonExtractorBuilder : JMeterLeafBuilder<JsonExtractor>() {
    override var name: String = "JSON Extractor"
    var applyTo: ApplyTo = ApplyTo.MAIN_SAMPLE_ONLY
    var jmeterVariableName: String = ""
    var referenceName: String = ""
    var jsonPathExpression: String = ""
    var matchNo: Int? = null
    var computeConcatenation: Boolean = false
    var defaultValue: String = ""

    override fun doBuild(): JsonExtractor = JsonExtractor(
        name = name,
        comment = comment,
        applyTo = applyTo,
        jmeterVariableName = jmeterVariableName,
        referenceName = referenceName,
        jsonPathExpression = jsonPathExpression,
        matchNo = matchNo,
        computeConcatenation = computeConcatenation,
        defaultValue = defaultValue,
        enabled = enabled,
    )
}
