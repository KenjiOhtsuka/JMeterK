package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.configelement.HttpHeaderManager

fun HttpHeaderManager.toJmxNode(): JmxElement {
    val headerElements = headers.map { h ->
        elementProp("", "Header", listOf(
            stringProp("Header.name", h.name),
            stringProp("Header.value", h.value)
        ))
    }

    val headersCollection = JmxElement(
        tag = "collectionProp",
        attributes = mapOf("name" to "HeaderManager.headers"),
        children = headerElements
    )

    return JmxElement(
        tag = "HeaderManager",
        attributes = buildMap {
            put("guiclass", "HeaderPanel")
            put("testclass", "HeaderManager")
            put("testname", name)
            if (!enabled) put("enabled", "false")
        },
        children = listOf(headersCollection)
    )
}
