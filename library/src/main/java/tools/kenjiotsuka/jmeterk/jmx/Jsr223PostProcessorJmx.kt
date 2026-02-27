package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.postprocessor.Jsr223PostProcessor

fun Jsr223PostProcessor.toJmxNode(): JmxElement = JmxElement(
    tag = "JSR223PostProcessor",
    attributes = buildMap {
        put("guiclass", "TestBeanGUI"); put("testclass", "JSR223PostProcessor"); put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = jsr223ScriptChildren()
)
