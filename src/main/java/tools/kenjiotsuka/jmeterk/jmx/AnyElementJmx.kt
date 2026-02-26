package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.core.AnyElement
import tools.kenjiotsuka.jmeterk.model.core.ConfigNode

fun AnyElement.toJmxNode(): JmxElement = JmxElement(
    tag = tagName,
    attributes = attributes,
    children = buildList {
        addAll(configNodes.map { it.toJmxNode() })
        if (value != null) add(JmxText(value))
        addAll(children.map { it.toJmxNode() })
    }
)

fun ConfigNode.toJmxNode(): JmxNode =
    if (configNodes.isEmpty() && value != null) {
        JmxElement(tag = tagName, attributes = attributes, children = listOf(JmxText(value)))
    } else {
        JmxElement(
            tag = tagName,
            attributes = attributes,
            children = buildList {
                addAll(configNodes.map { it.toJmxNode() })
                if (value != null) add(JmxText(value))
            }
        )
    }
