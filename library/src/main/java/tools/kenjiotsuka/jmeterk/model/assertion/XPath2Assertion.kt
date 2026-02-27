package tools.kenjiotsuka.jmeterk.model.assertion

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory

data class XPath2Assertion(
    override val name: String,
    override val comment: String,
    /**
     * The XPath 2.0 expression to evaluate against the response (GUI: unlabeled text area).
     * Default: `"/"`.
     */
    val xPath: String,
    /**
     * Invert the assertion result (GUI: "Invert assertion (will fail if XPath expression matches)").
     * Default: false.
     */
    val invertAssertion: Boolean,
    /**
     * Namespace prefix-to-URI mappings, one per line in `prefix=namespace` format
     * (GUI: "Namespaces aliases list (prefix=full namespace, 1 per line)").
     * Default: `""` (empty).
     */
    val namespacesAliasesList: String,
    /** Which samples to apply this assertion to (GUI: "Apply to"). Default: [ApplyTo.MAIN_SAMPLE_ONLY]. */
    val applyTo: ApplyTo,
    /**
     * JMeter variable name whose value is checked (GUI: "JMeter Variable Name to use").
     * Only used when [applyTo] is [ApplyTo.JMETER_VARIABLE].
     */
    val jmeterVariableName: String,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled)

class XPath2AssertionBuilder : JMeterLeafBuilder<XPath2Assertion>() {
    override var name: String = "XPath2 Assertion"
    /**
     * The XPath 2.0 expression to evaluate (GUI: unlabeled text area).
     * Default: `"/"`.
     */
    var xPath: String = "/"
    /**
     * Invert the assertion: fail when the XPath expression matches.
     * GUI disables other fields when this is checked, but values are always serialized.
     * Default: false.
     */
    var invertAssertion: Boolean = false
    /**
     * Namespace prefix-to-URI mappings, one per line (`prefix=namespace`).
     * Default: `""` (empty).
     */
    var namespacesAliasesList: String = ""
    /** Which samples to apply this assertion to. Default: [ApplyTo.MAIN_SAMPLE_ONLY]. */
    var applyTo: ApplyTo = ApplyTo.MAIN_SAMPLE_ONLY
    /**
     * JMeter variable name to test.
     * GUI disables this field when [applyTo] is not [ApplyTo.JMETER_VARIABLE], but value is always serialized.
     */
    var jmeterVariableName: String = ""

    /**
     * Validates [xPath] as an XPath expression using the JDK's built-in XPath 1.0 engine.
     * Returns `true` if the expression compiles without error, `false` otherwise.
     *
     * Note: The JDK engine only supports XPath 1.0 syntax. Some valid XPath 2.0 constructs
     * may be reported as invalid. JMeter accepts any expression regardless of validation result.
     */
    fun validate(): Boolean = try {
        XPathFactory.newInstance().newXPath().compile(xPath)
        true
    } catch (e: XPathExpressionException) {
        false
    }

    override fun doBuild(): XPath2Assertion = XPath2Assertion(
        name = name,
        comment = comment,
        xPath = xPath,
        invertAssertion = invertAssertion,
        namespacesAliasesList = namespacesAliasesList,
        applyTo = applyTo,
        jmeterVariableName = jmeterVariableName,
        enabled = enabled
    )
}
