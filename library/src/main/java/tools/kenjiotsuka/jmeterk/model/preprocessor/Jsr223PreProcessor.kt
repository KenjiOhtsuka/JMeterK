package tools.kenjiotsuka.jmeterk.model.preprocessor

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.jsr223.AbstractJsr223LeafBuilder
import tools.kenjiotsuka.jmeterk.model.jsr223.Jsr223Language
import tools.kenjiotsuka.jmeterk.model.jsr223.Jsr223ScriptElement

data class Jsr223PreProcessor(
    override val name: String,
    override val comment: String,
    /** Scripting language. Ignored when [customLanguage] is set. */
    override val language: Jsr223Language,
    /**
     * Custom scripting language typed in the GUI text field.
     * When non-null, takes precedence over [language] in JMX output.
     */
    override val customLanguage: String?,
    /**
     * Inline script to execute (GUI: script text area).
     * Variables available: ctx, vars, props, sampler, log, Label, Filename, Parameters, args, OUT.
     */
    override val script: String,
    /** Path to an external script file. Takes precedence over [script]. */
    override val filename: String,
    /** Parameters passed to the script as the variable `Parameters`. */
    override val parameters: String,
    /** GUI: "Cache compiled script if available" checkbox. Default: true. */
    override val cacheCompiledScriptIfAvailable: Boolean,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled), Jsr223ScriptElement

class Jsr223PreProcessorBuilder : AbstractJsr223LeafBuilder<Jsr223PreProcessor>() {
    override var name: String = "JSR223 PreProcessor"

    override fun doBuild(): Jsr223PreProcessor = Jsr223PreProcessor(
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
