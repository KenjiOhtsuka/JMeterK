package tools.kenjiotsuka.jmeterk.model.assertion

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class SizeAssertion(
    override val name: String,
    override val comment: String,
    /** Which samples to apply this assertion to (GUI: "Apply to"). Default: [ApplyTo.MAIN_SAMPLE_ONLY]. */
    val applyTo: ApplyTo,
    /**
     * JMeter variable name to read the size from (GUI: "JMeter Variable Name to use").
     * Only used when [applyTo] is [ApplyTo.JMETER_VARIABLE].
     */
    val jmeterVariableName: String,
    /** Which part of the response to measure (GUI: "Response Size Field to Test"). */
    val responseField: ResponseField,
    /** Expected size in bytes (GUI: "Size to Assert" → "Size in bytes"). `null` means not set (serialized as empty string). Default: null. */
    val size: Long?,
    /** How to compare the actual size to [size] (GUI: "Size to Assert" → "Type of Comparison" radio buttons: =, !=, >, <, >=, <=). Default: [ComparisonOperator.EQUAL]. */
    val comparisonOperator: ComparisonOperator,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled) {

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
    /** Which samples to apply this assertion to. Default: [ApplyTo.MAIN_SAMPLE_ONLY]. */
    var applyTo: ApplyTo = ApplyTo.MAIN_SAMPLE_ONLY
    /**
     * JMeter variable name to read the size from.
     * Only used when [applyTo] is [SizeAssertion.ApplyTo.JMETER_VARIABLE].
     */
    var jmeterVariableName: String = ""
    /** Response field to measure. Default: [SizeAssertion.ResponseField.FULL_RESPONSE]. */
    var responseField: SizeAssertion.ResponseField = SizeAssertion.ResponseField.FULL_RESPONSE
    /** Expected size in bytes (GUI: "Size to Assert" → "Size in bytes"). `null` means not set (serialized as empty string). Default: null. */
    var size: Long? = null
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
