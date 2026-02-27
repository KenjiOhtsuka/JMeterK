package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.jsr223.Jsr223ScriptElement

/** Returns the JMX child props shared by all JSR223 script elements. */
internal fun Jsr223ScriptElement.jsr223ScriptChildren(): List<JmxNode> = buildList {
    add(stringProp("cacheKey", cacheCompiledScriptIfAvailable.toString()))
    add(stringProp("filename", filename))
    add(stringProp("parameters", parameters))
    add(stringProp("script", script))
    add(stringProp("scriptLanguage", customLanguage ?: language.scriptLanguage))
    if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
}
