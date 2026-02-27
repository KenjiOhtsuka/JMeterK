package tools.kenjiotsuka.jmeterk.model.assertion

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class JsonAssertion(
    override val name: String,
    override val comment: String,
    /** JSON Path expression to assert (GUI: "Assert JSON Path exists"). */
    val jsonPath: String,
    /**
     * When `true`, also assert the extracted value (GUI: "Additionally assert value").
     * Enables [expectedValue] and [matchAsRegularExpression].
     */
    val additionallyAssertValue: Boolean,
    /**
     * Treat [expectedValue] as a regular expression (GUI: "Match as regular expression").
     * The GUI disables this field when [additionallyAssertValue] is `false` or [expectNull] is `true`,
     * but the value is always serialized to JMX regardless.
     */
    val matchAsRegularExpression: Boolean,
    /**
     * Value to assert against the extracted JSON value (GUI: "Expected Value").
     * The GUI disables this field when [additionallyAssertValue] is `false` or [expectNull] is `true`,
     * but the value is always serialized to JMX regardless.
     */
    val expectedValue: String,
    /**
     * Assert that the extracted value is null (GUI: "Expect null").
     * When `true`, [expectedValue] and [matchAsRegularExpression] are ignored.
     */
    val expectNull: Boolean,
    /** Invert the assertion result (GUI: "Invert assertion (will fail if above condition met)"). */
    val invertAssertion: Boolean,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled)

class JsonAssertionBuilder : JMeterLeafBuilder<JsonAssertion>() {
    override var name: String = "JSON Assertion"
    /** JSON Path expression to assert (e.g. `"$.status"`). */
    var jsonPath: String = ""
    /**
     * Enable value assertion. When `true`, [expectedValue] and [matchAsRegularExpression] are used.
     * Default: false.
     */
    var additionallyAssertValue: Boolean = false
    /**
     * Treat [expectedValue] as a regular expression. Default: true.
     * The GUI disables this field when [additionallyAssertValue] is `false` or [expectNull] is `true`,
     * but the value is always serialized to JMX regardless.
     */
    var matchAsRegularExpression: Boolean = true
    /**
     * Expected value to assert. The GUI disables this field when [additionallyAssertValue] is `false`
     * or [expectNull] is `true`, but the value is always serialized to JMX regardless.
     */
    var expectedValue: String = ""
    /**
     * Assert that the extracted value is null. When `true`, [expectedValue] and
     * [matchAsRegularExpression] are not evaluated. Default: false.
     */
    var expectNull: Boolean = false
    /** Invert the assertion: fail if the condition is met. Default: false. */
    var invertAssertion: Boolean = false

    override fun doBuild(): JsonAssertion = JsonAssertion(
        name = name,
        comment = comment,
        jsonPath = jsonPath,
        additionallyAssertValue = additionallyAssertValue,
        matchAsRegularExpression = matchAsRegularExpression,
        expectedValue = expectedValue,
        expectNull = expectNull,
        invertAssertion = invertAssertion,
        enabled = enabled
    )
}
