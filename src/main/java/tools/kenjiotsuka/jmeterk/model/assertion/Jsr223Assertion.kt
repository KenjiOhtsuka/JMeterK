package tools.kenjiotsuka.jmeterk.model.assertion

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class Jsr223Assertion(
    override val name: String,
    override val comment: String,
    /**
     * Scripting language selected from the dropdown.
     * Ignored when [customLanguage] is set.
     */
    val language: Language,
    /**
     * Custom scripting language string typed in the text field.
     * When non-null, takes precedence over [language] in JMX output.
     */
    val customLanguage: String?,
    /** Inline script to execute. Ignored if filename is set. */
    val script: String,
    /** Path to an external script file. Takes precedence over script. */
    val filename: String,
    /** Parameters passed to the script as the variable `Parameters`. */
    val parameters: String,
    /** Corresponds to "Cache compiled script if available" checkbox in the JMeter GUI. */
    val cacheCompiledScriptIfAvailable: Boolean,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled) {

    /**
     * Scripting languages available in the JSR223 Assertion GUI dropdown.
     * [scriptLanguage] is the identifier used in the JMX file.
     */
    enum class Language(val scriptLanguage: String) {
        BEAN_SHELL("beanshell"),
        GROOVY("groovy"),
        JAVA("java"),
        JAVASCRIPT("javascript"),
        JEXL("jexl"),
        JEXL2("jexl2"),
        JEXL3("jexl3"),
        NASHORN("nashorn"),
    }
}

class Jsr223AssertionBuilder : JMeterLeafBuilder<Jsr223Assertion>() {
    override var name: String = "JSR223 Assertion"

    /**
     * Scripting language selected from the GUI dropdown.
     * Defaults to [Jsr223Assertion.Language.GROOVY].
     * To use a language not in the dropdown, set [customLanguage] instead.
     */
    var language: Jsr223Assertion.Language = Jsr223Assertion.Language.GROOVY

    /**
     * Custom scripting language typed in the GUI text field.
     * When non-null, takes precedence over [language] in the JMX output.
     */
    var customLanguage: String? = null

    var script: String = ""
    var filename: String = ""
    var parameters: String = ""
    /** Corresponds to "Cache compiled script if available" checkbox in the JMeter GUI. */
    var cacheCompiledScriptIfAvailable: Boolean = true

    override fun doBuild(): Jsr223Assertion = Jsr223Assertion(
        name = name,
        comment = comment,
        language = language,
        customLanguage = customLanguage,
        script = script,
        filename = filename,
        parameters = parameters,
        cacheCompiledScriptIfAvailable = cacheCompiledScriptIfAvailable,
        enabled = enabled
    )
}
