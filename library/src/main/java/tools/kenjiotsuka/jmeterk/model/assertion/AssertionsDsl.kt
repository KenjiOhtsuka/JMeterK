package tools.kenjiotsuka.jmeterk.model.assertion

import tools.kenjiotsuka.jmeterk.model.core.JMeterElement

interface AssertionsDsl {
    fun add(child: JMeterElement)

    fun responseAssertion(block: ResponseAssertionBuilder.() -> Unit) =
        add(ResponseAssertionBuilder().apply(block).build())

    fun jsr223Assertion(block: Jsr223AssertionBuilder.() -> Unit) =
        add(Jsr223AssertionBuilder().apply(block).build())

    fun jsonAssertion(block: JsonAssertionBuilder.() -> Unit) =
        add(JsonAssertionBuilder().apply(block).build())

    fun sizeAssertion(block: SizeAssertionBuilder.() -> Unit) =
        add(SizeAssertionBuilder().apply(block).build())
    fun xpath2Assertion(block: XPath2AssertionBuilder.() -> Unit) =
        add(XPath2AssertionBuilder().apply(block).build())
}
