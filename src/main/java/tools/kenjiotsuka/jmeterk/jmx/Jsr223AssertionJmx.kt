package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.Jsr223Assertion

fun Jsr223Assertion.toJmxNode(): JmxElement = JmxElement(
    tag = "JSR223Assertion",
    attributes = mapOf(
        "guiclass"  to "TestBeanGUI",
        "testclass" to "JSR223Assertion",
        "testname"  to name,
        "enabled"   to enabled.toString()
    ),
    children = listOf(
        stringProp("scriptLanguage", scriptLanguage),
        stringProp("parameters", parameters),
        stringProp("filename", filename),
        stringProp("cacheKey", cacheKey),
        stringProp("script", script)
    )
)
