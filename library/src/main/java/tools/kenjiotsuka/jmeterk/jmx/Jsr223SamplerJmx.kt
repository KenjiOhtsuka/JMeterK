package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.sampler.Jsr223Sampler

fun Jsr223Sampler.toJmxNode(): JmxElement = JmxElement(
    tag = "JSR223Sampler",
    attributes = buildMap {
        put("guiclass", "TestBeanGUI"); put("testclass", "JSR223Sampler"); put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = jsr223ScriptChildren()
)
