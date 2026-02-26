package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.core.AnyElement
import tools.kenjiotsuka.jmeterk.model.core.JMeterElement
import tools.kenjiotsuka.jmeterk.model.core.TestPlan
import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroup

fun JMeterElement.toJmxNode(): JmxNode = when (this) {
    // root node
    is TestPlan    -> toJmxNode()
    // Threads
    is ThreadGroup -> toJmxNode()
    // Config Element

    // Listener

    // Timer

    // Pre Processors

    // Post Processors

    // Assertions

    // Test Fragment

    // Non-Test Elements
    is AnyElement  -> toJmxNode()
    else -> throw IllegalArgumentException("Unsupported element: ${this::class.simpleName}")
}
