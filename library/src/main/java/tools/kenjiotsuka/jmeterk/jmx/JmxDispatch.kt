package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.assertion.Jsr223Assertion
import tools.kenjiotsuka.jmeterk.model.assertion.JsonAssertion
import tools.kenjiotsuka.jmeterk.model.assertion.ResponseAssertion
import tools.kenjiotsuka.jmeterk.model.assertion.SizeAssertion
import tools.kenjiotsuka.jmeterk.model.assertion.XPath2Assertion
import tools.kenjiotsuka.jmeterk.model.configelement.HttpHeaderManager
import tools.kenjiotsuka.jmeterk.model.core.AnyElement
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer
import tools.kenjiotsuka.jmeterk.model.core.JMeterElement
import tools.kenjiotsuka.jmeterk.model.core.TestPlan
import tools.kenjiotsuka.jmeterk.model.logiccontroller.IfController
import tools.kenjiotsuka.jmeterk.model.logiccontroller.LoopController
import tools.kenjiotsuka.jmeterk.model.logiccontroller.TransactionController
import tools.kenjiotsuka.jmeterk.model.logiccontroller.WhileController
import tools.kenjiotsuka.jmeterk.model.postprocessor.Jsr223PostProcessor
import tools.kenjiotsuka.jmeterk.model.preprocessor.Jsr223PreProcessor
import tools.kenjiotsuka.jmeterk.model.sampler.DebugSampler
import tools.kenjiotsuka.jmeterk.model.sampler.FlowControlAction
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest
import tools.kenjiotsuka.jmeterk.model.sampler.Jsr223Sampler
import tools.kenjiotsuka.jmeterk.model.thread.OpenModelThreadGroup
import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroup

fun JMeterElement.toJmxNode(): JmxNode = when (this) {
    // root node
    is TestPlan         -> toJmxNode()
    // Threads
    is ThreadGroup            -> toJmxNode()
    is OpenModelThreadGroup   -> toJmxNode()
    // Samplers
    is HttpRequest       -> toJmxNode()
    is DebugSampler      -> toJmxNode()
    is FlowControlAction -> toJmxNode()
    is Jsr223Sampler     -> toJmxNode()
    // Pre Processors
    is Jsr223PreProcessor  -> toJmxNode()
    // Post Processors
    is Jsr223PostProcessor -> toJmxNode()
    // Config Element
    is HttpHeaderManager -> toJmxNode()
    // Logic Controllers
    is IfController          -> toJmxNode()
    is LoopController        -> toJmxNode()
    is WhileController       -> toJmxNode()
    is TransactionController -> toJmxNode()

    // Listener

    // Timer

    // Assertions
    is ResponseAssertion -> toJmxNode()
    is Jsr223Assertion   -> toJmxNode()
    is JsonAssertion     -> toJmxNode()
    is SizeAssertion     -> toJmxNode()
    is XPath2Assertion   -> toJmxNode()

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

private const val JMETER_VERSION = "5.6.3"

/** Serializes a [TestPlan] to a complete JMX XML document string including the XML declaration. */
fun buildJmxDocument(plan: TestPlan): String {
    val root = JmxElement(
        "jmeterTestPlan",
        mapOf("version" to "1.2", "properties" to "5.0", "jmeter" to JMETER_VERSION),
        listOf(JmxHashTree(plan.toJmxSubtree()))
    )
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n${JmxConverter().convert(root)}"
}
