package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.Jsr223Assertion

fun Jsr223Assertion.toJmxNode(): JmxElement = JmxElement(
    tag = "JSR223Assertion",
    attributes = buildMap {
        put("guiclass", "TestBeanGUI"); put("testclass", "JSR223Assertion"); put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = listOf(
        stringProp("cacheKey", cacheCompiledScriptIfAvailable.toString()),
        stringProp("filename", filename),
        stringProp("parameters", parameters),
        stringProp("script", script),
        stringProp("scriptLanguage", customLanguage ?: language.scriptLanguage),
        stringProp("TestPlan.comments", comment)
    )
)
