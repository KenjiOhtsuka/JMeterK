package tools.kenjiotsuka.jmeterk.model.assertion

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class Jsr223Assertion(
    override val name: String,
    override val comment: String,
    /** Scripting language (e.g. "groovy", "javascript"). Defaults to groovy. */
    val scriptLanguage: String,
    /** Inline script to execute. Ignored if filename is set. */
    val script: String,
    /** Path to an external script file. Takes precedence over script. */
    val filename: String,
    /** Parameters passed to the script as the variable `Parameters`. */
    val parameters: String,
    /**
     * Cache key for compiled script. When non-empty, the compiled script is cached
     * using this key to avoid recompilation on each iteration.
     */
    val cacheKey: String,
    override val enabled: Boolean
): JMeterLeaf(name, comment, enabled) {

}

class Jsr223AssertionBuilder : JMeterLeafBuilder<Jsr223Assertion>() {
    override var name: String = "JSR223 Assertion"
    var scriptLanguage: String = "groovy"
    var script: String = ""
    var filename: String = ""
    var parameters: String = ""
    var cacheKey: String = ""

    override fun doBuild(): Jsr223Assertion = Jsr223Assertion(
        name = name,
        comment = comment,
        scriptLanguage = scriptLanguage,
        script = script,
        filename = filename,
        parameters = parameters,
        cacheKey = cacheKey,
        enabled = enabled
    )
}

fun jsr223Assertion(block: Jsr223AssertionBuilder.() -> Unit): Jsr223Assertion {
    return Jsr223AssertionBuilder().apply(block).build()
}
