package tools.kenjiotsuka.jmeterk.model.postprocessor

import tools.kenjiotsuka.jmeterk.model.assertion.ApplyTo
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class JsonJmesPathExtractor(
    override val name: String,
    override val comment: String,
    /** GUI: "Apply to" radio buttons. Default: [ApplyTo.MAIN_SAMPLE_ONLY]. */
    val applyTo: ApplyTo,
    /**
     * JMeter variable name used when [applyTo] is [ApplyTo.JMETER_VARIABLE].
     * JMX: `Scope.variable`.
     */
    val jmeterVariableName: String,
    /** GUI: "Names of Created Variables". JMX: `JMESExtractor.referenceName`. */
    val referenceName: String,
    /** GUI: "JSON Path Expressions". JMX: `JMESExtractor.jmesPathExpr`. */
    val jmesPathExpression: String,
    /** GUI: "Match No. (0 for Random)". JMX: `JMESExtractor.matchNumber`. `null` = not set (empty string). */
    val matchNo: Int?,
    /** GUI: "Compute concatenation var (suffix _ALL)" checkbox. JMX: `JMESExtractor.compute_concat`. Default: false. */
    val computeConcatenation: Boolean,
    /**
     * GUI: "Default Values". JMX: `JMESExtractor.defaultValues`.
     * Only emitted when non-empty.
     */
    val defaultValue: String,
    override val enabled: Boolean,
) : JMeterLeaf(name, comment, enabled)

class JsonJmesPathExtractorBuilder : JMeterLeafBuilder<JsonJmesPathExtractor>() {
    override var name: String = "JSON JMESPath Extractor"
    var applyTo: ApplyTo = ApplyTo.MAIN_SAMPLE_ONLY
    var jmeterVariableName: String = ""
    var referenceName: String = ""
    var jmesPathExpression: String = ""
    var matchNo: Int? = null
    var computeConcatenation: Boolean = false
    var defaultValue: String = ""

    override fun doBuild(): JsonJmesPathExtractor = JsonJmesPathExtractor(
        name = name,
        comment = comment,
        applyTo = applyTo,
        jmeterVariableName = jmeterVariableName,
        referenceName = referenceName,
        jmesPathExpression = jmesPathExpression,
        matchNo = matchNo,
        computeConcatenation = computeConcatenation,
        defaultValue = defaultValue,
        enabled = enabled,
    )
}
