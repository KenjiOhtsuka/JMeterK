package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.Jsr223Assertion
import tools.kenjiotsuka.jmeterk.model.assertion.ResponseAssertion
import tools.kenjiotsuka.jmeterk.model.core.AnyElement
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer
import tools.kenjiotsuka.jmeterk.model.core.JMeterElement
import tools.kenjiotsuka.jmeterk.model.core.TestPlan
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest
import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroup

fun JMeterElement.toJmxNode(): JmxNode = when (this) {
    // root node
    is TestPlan         -> toJmxNode()
    // Threads
    is ThreadGroup      -> toJmxNode()
    // Samplers
    is HttpRequest      -> toJmxNode()
    // Config Element

    // Listener

    // Timer

    // Pre Processors

    // Post Processors

    // Assertions
    is ResponseAssertion -> toJmxNode()
    is Jsr223Assertion   -> toJmxNode()

    // Test Fragment

    // Non-Test Elements
    is AnyElement       -> toJmxNode()
    else -> throw IllegalArgumentException("Unsupported element: ${this::class.simpleName}")
}

/**
 * Returns [element, JmxHashTree(children)] — the flat pair structure that JMeter uses in JMX files.
 * GUI children are placed in the sibling hashTree, NOT nested inside the element's XML tag.
 */
fun JMeterElement.toJmxSubtree(): List<JmxNode> {
    val element = toJmxNode()
    val childNodes = if (this is JMeterContainer) children.flatMap { it.toJmxSubtree() } else emptyList()
    return listOf(element, JmxHashTree(childNodes))
}

/** Serializes a [TestPlan] to a complete JMX XML document string including the XML declaration. */
fun buildJmxDocument(plan: TestPlan): String {
    val root = JmxElement(
        "jmeterTestPlan",
        mapOf("version" to "1.2", "properties" to "5.0", "jmeter" to "5.6.3"),
        listOf(JmxHashTree(plan.toJmxSubtree()))
    )
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n${JmxConverter().convert(root)}"
}
