package tools.kenjiotsuka.jmeterk.model.jsr223

/** Concrete holder of JSR223 script field defaults, used via Kotlin `by` delegation. */
class Jsr223ScriptDelegate : Jsr223ScriptBuilder {
    override var language: Jsr223Language = Jsr223Language.GROOVY
    override var customLanguage: String? = null
    override var script: String = ""
    override var filename: String = ""
    override var parameters: String = ""
    override var cacheCompiledScriptIfAvailable: Boolean = true
}
