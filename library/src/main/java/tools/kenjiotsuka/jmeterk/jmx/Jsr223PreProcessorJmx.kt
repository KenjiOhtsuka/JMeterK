package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.preprocessor.Jsr223PreProcessor

fun Jsr223PreProcessor.toJmxNode(): JmxElement = JmxElement(
    tag = "JSR223PreProcessor",
    attributes = buildMap {
        put("guiclass", "TestBeanGUI"); put("testclass", "JSR223PreProcessor"); put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = jsr223ScriptChildren()
)
