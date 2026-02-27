package tools.kenjiotsuka.jmeterk.model.assertion

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class SizeAssertion(
    override val name: String,
    override val comment: String,
    /** Which samples to apply this assertion to (GUI: "Apply to"). */
    val applyTo: ApplyTo,
    /**
     * JMeter variable name to read the size from (GUI: "JMeter Variable Name to use").
     * Only used when [applyTo] is [ApplyTo.JMETER_VARIABLE].
     */
    val jmeterVariableName: String,
    /** Which part of the response to measure (GUI: "Response Size Field to Test"). */
    val responseField: ResponseField,
    /** Expected size in bytes (GUI: "Size to Assert" → "Size in bytes" text box). */
    val size: Long,
    /** How to compare the actual size to [size] (GUI: "Size to Assert" → "Type of Comparison" radio buttons: =, !=, >, <, >=, <=). Default: [ComparisonOperator.EQUAL]. */
    val comparisonOperator: ComparisonOperator,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled) {

    /** GUI: "Apply to" radio button group. */
    enum class ApplyTo {
        /** Main sample and sub-samples. JMX value: `"all"`. */
        MAIN_SAMPLE_AND_SUB_SAMPLES,
        /** Main sample only (default). JMX: `Assertion.scope` not emitted. */
        MAIN_SAMPLE_ONLY,
        /** Sub-samples only. JMX value: `"children"`. */
        SUB_SAMPLES_ONLY,
        /** Read size from a JMeter variable. JMX value: `"variable"` + `Scope.variable`. */
        JMETER_VARIABLE
    }

    /** GUI: "Response Size Field to Test" radio button group. */
    enum class ResponseField {
        /** Full response (network size). JMX: `SizeAssertion.response_network_size`. Default. */
        FULL_RESPONSE,
        /** Response headers only. JMX: `SizeAssertion.response_headers`. */
        RESPONSE_HEADERS,
        /** Response body only. JMX: `SizeAssertion.response_data`. */
        RESPONSE_BODY,
        /** Response code string length. JMX: `SizeAssertion.response_code`. */
        RESPONSE_CODE,
        /** Response message string length. JMX: `SizeAssertion.response_message`. */
        RESPONSE_MESSAGE
    }

    /** GUI: comparator buttons (=, !=, >, <, >=, <=). JMX integer values 1–6. */
    enum class ComparisonOperator(val jmxValue: Int) {
        EQUAL(1),
        NOT_EQUAL(2),
        GREATER_THAN(3),
        LESS_THAN(4),
        GREATER_THAN_OR_EQUAL(5),
        LESS_THAN_OR_EQUAL(6)
    }
}

class SizeAssertionBuilder : JMeterLeafBuilder<SizeAssertion>() {
    override var name: String = "Size Assertion"
    /** Which samples to apply this assertion to. Default: [SizeAssertion.ApplyTo.MAIN_SAMPLE_ONLY]. */
    var applyTo: SizeAssertion.ApplyTo = SizeAssertion.ApplyTo.MAIN_SAMPLE_ONLY
    /**
     * JMeter variable name to read the size from.
     * Only used when [applyTo] is [SizeAssertion.ApplyTo.JMETER_VARIABLE].
     */
    var jmeterVariableName: String = ""
    /** Response field to measure. Default: [SizeAssertion.ResponseField.FULL_RESPONSE]. */
    var responseField: SizeAssertion.ResponseField = SizeAssertion.ResponseField.FULL_RESPONSE
    /** Expected size in bytes (GUI: "Size to Assert" → "Size in bytes"). Default: 0. */
    var size: Long = 0L
    /** Type of comparison (GUI: "Size to Assert" → "Type of Comparison": =, !=, >, <, >=, <=). Default: [SizeAssertion.ComparisonOperator.EQUAL]. */
    var comparisonOperator: SizeAssertion.ComparisonOperator = SizeAssertion.ComparisonOperator.EQUAL

    override fun doBuild(): SizeAssertion = SizeAssertion(
        name = name,
        comment = comment,
        applyTo = applyTo,
        jmeterVariableName = jmeterVariableName,
        responseField = responseField,
        size = size,
        comparisonOperator = comparisonOperator,
        enabled = enabled
    )
}
