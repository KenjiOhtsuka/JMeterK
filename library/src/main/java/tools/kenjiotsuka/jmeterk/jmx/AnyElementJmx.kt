package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.core.AnyElement
import tools.kenjiotsuka.jmeterk.model.core.ConfigNode

fun AnyElement.toJmxNode(): JmxElement = JmxElement(
    tag = tagName,
    attributes = buildMap {
        putAll(attributes)
        // Auto-inject testname from name if not explicitly set in attributes.
        // This avoids double-managing name and testname separately.
        if (!containsKey("testname") && name.isNotEmpty()) put("testname", name)
    },
    children = buildList {
        addAll(configNodes.map { it.toJmxNode() })
        if (value != null) add(JmxText(value))
        // GUI children are handled by toJmxSubtree(), not placed inside the element tag
    }
)

fun ConfigNode.toJmxNode(): JmxNode = JmxElement(
    tag = tagName,
    attributes = attributes,
    children = buildList {
        addAll(configNodes.map { it.toJmxNode() })
        if (value != null) add(JmxText(value))
    }
)
