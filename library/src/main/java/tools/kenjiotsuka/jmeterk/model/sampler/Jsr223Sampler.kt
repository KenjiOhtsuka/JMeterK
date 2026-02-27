package tools.kenjiotsuka.jmeterk.model.sampler

import tools.kenjiotsuka.jmeterk.model.assertion.AssertionsDsl
import tools.kenjiotsuka.jmeterk.model.configelement.ConfigElementsDsl
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainerBuilder
import tools.kenjiotsuka.jmeterk.model.jsr223.Jsr223Language
import tools.kenjiotsuka.jmeterk.model.jsr223.Jsr223ScriptBuilder
import tools.kenjiotsuka.jmeterk.model.jsr223.Jsr223ScriptDelegate
import tools.kenjiotsuka.jmeterk.model.jsr223.Jsr223ScriptElement

data class Jsr223Sampler(
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
     * Variables available: ctx, vars, props, SampleResult, sampler, log, Label, Filename, Parameters, args, OUT.
     */
    override val script: String,
    /** Path to an external script file. Takes precedence over [script]. */
    override val filename: String,
    /** Parameters passed to the script as the variable `Parameters`. */
    override val parameters: String,
    /** GUI: "Cache compiled script if available" checkbox. Default: true. */
    override val cacheCompiledScriptIfAvailable: Boolean,
    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled), Jsr223ScriptElement

class Jsr223SamplerBuilder(
    private val d: Jsr223ScriptDelegate = Jsr223ScriptDelegate()
) : JMeterContainerBuilder<Jsr223Sampler>(), AssertionsDsl, ConfigElementsDsl, Jsr223ScriptBuilder by d {
    override var name: String = "JSR223 Sampler"

    override fun doBuild(): Jsr223Sampler = Jsr223Sampler(
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
