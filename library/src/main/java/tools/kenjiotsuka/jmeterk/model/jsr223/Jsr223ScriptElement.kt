package tools.kenjiotsuka.jmeterk.model.jsr223

/** Read-only contract shared by all JSR223 model data classes. Used by JMX serialization. */
interface Jsr223ScriptElement {
    val language: Jsr223Language
    val customLanguage: String?
    val script: String
    val filename: String
    val parameters: String
    val cacheCompiledScriptIfAvailable: Boolean
    val comment: String
}
