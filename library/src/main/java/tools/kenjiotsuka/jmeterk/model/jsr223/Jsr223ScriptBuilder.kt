package tools.kenjiotsuka.jmeterk.model.jsr223

/** Mutable contract shared by all JSR223 builder classes (both leaf and container variants). */
interface Jsr223ScriptBuilder {
    var language: Jsr223Language
    var customLanguage: String?
    var script: String
    var filename: String
    var parameters: String
    var cacheCompiledScriptIfAvailable: Boolean
}
