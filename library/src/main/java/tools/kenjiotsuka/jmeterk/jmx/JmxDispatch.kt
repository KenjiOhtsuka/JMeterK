package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.Jsr223Assertion
import tools.kenjiotsuka.jmeterk.model.assertion.JsonAssertion
import tools.kenjiotsuka.jmeterk.model.assertion.ResponseAssertion
import tools.kenjiotsuka.jmeterk.model.configelement.HttpHeaderManager
import tools.kenjiotsuka.jmeterk.model.core.AnyElement
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer
import tools.kenjiotsuka.jmeterk.model.core.JMeterElement
import tools.kenjiotsuka.jmeterk.model.core.TestPlan
import tools.kenjiotsuka.jmeterk.model.logiccontroller.IfController
import tools.kenjiotsuka.jmeterk.model.logiccontroller.LoopController
import tools.kenjiotsuka.jmeterk.model.logiccontroller.WhileController
import tools.kenjiotsuka.jmeterk.model.sampler.DebugSampler
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest
import tools.kenjiotsuka.jmeterk.model.thread.OpenModelThreadGroup
import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroup

fun JMeterElement.toJmxNode(): JmxNode = when (this) {
    // root node
    is TestPlan         -> toJmxNode()
    // Threads
    is ThreadGroup            -> toJmxNode()
    is OpenModelThreadGroup   -> toJmxNode()
    // Samplers
    is HttpRequest      -> toJmxNode()
    is DebugSampler     -> toJmxNode()
    // Config Element
    is HttpHeaderManager -> toJmxNode()
    // Logic Controllers
    is IfController     -> toJmxNode()
    is LoopController   -> toJmxNode()
    is WhileController  -> toJmxNode()

    // Listener

    // Timer

    // Pre Processors

    // Post Processors

    // Assertions
    is ResponseAssertion -> toJmxNode()
    is Jsr223Assertion   -> toJmxNode()
    is JsonAssertion     -> toJmxNode()

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
