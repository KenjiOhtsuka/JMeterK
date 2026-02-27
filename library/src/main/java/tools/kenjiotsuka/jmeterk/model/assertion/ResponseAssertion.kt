package tools.kenjiotsuka.jmeterk.model.assertion

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class ResponseAssertion(
    override val name: String,
    override val comment: String,
    /** Which part of the response to test against. */
    val fieldToTest: FieldToTest,
    val patternMatchingRule: PatternMatchingRule,
    /** If true, mark the sample as successful before asserting (ignores HTTP error codes). */
    val ignoreStatus: Boolean,
    /** Invert the result of the matching rule. */
    val not: Boolean,
    /** Treat each pattern as OR condition instead of AND. */
    val or: Boolean,
    /** Patterns to test against the target field. */
    val patterns: List<String>,
    /** Custom failure message shown when the assertion fails. Empty string means no custom message. */
    val customFailureMessage: String,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled) {

    enum class FieldToTest {
        TEXT_RESPONSE,
        RESPONSE_CODE,
        RESPONSE_MESSAGE,
        RESPONSE_HEADERS,
        REQUEST_HEADERS,
        URL_SAMPLED,
        DOCUMENT_TEXT,
        REQUEST_DATA
    }

    enum class PatternMatchingRule {
        /** Target contains the pattern string (regex). */
        CONTAINS,
        /** Target matches the pattern string as a full regex. */
        MATCHES,
        /** Target equals the pattern string exactly. */
        EQUALS,
        /** Target contains the pattern string (plain substring, no regex). */
        SUBSTRING,
    }

}

class ResponseAssertionBuilder : JMeterLeafBuilder<ResponseAssertion>() {
    override var name: String = "Response Assertion"
    /** Which part of the response to test against. */
    var fieldToTest: ResponseAssertion.FieldToTest = ResponseAssertion.FieldToTest.TEXT_RESPONSE
    /** If true, mark the sample as successful before asserting (ignores HTTP error codes). */
    var ignoreStatus: Boolean = false
    var patternMatchingRule: ResponseAssertion.PatternMatchingRule = ResponseAssertion.PatternMatchingRule.SUBSTRING
    /** Invert the result of the matching rule. */
    var not: Boolean = false
    /** Treat each pattern as OR condition instead of AND. */
    var or: Boolean = false
    /** Patterns to test against the target field. */
    val patterns: MutableList<String> = mutableListOf()
    /** Custom failure message shown when the assertion fails. Empty string means no custom message. */
    var customFailureMessage: String = ""

    override fun doBuild(): ResponseAssertion = ResponseAssertion(
        name = name,
        comment = comment,
        fieldToTest = fieldToTest,
        ignoreStatus = ignoreStatus,
        patternMatchingRule = patternMatchingRule,
        not = not,
        or = or,
        patterns = patterns.toList(),
        customFailureMessage = customFailureMessage,
        enabled = enabled
    )
}


