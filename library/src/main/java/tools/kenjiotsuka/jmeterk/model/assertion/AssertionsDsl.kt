package tools.kenjiotsuka.jmeterk.model.assertion

import tools.kenjiotsuka.jmeterk.model.core.JMeterElement

interface AssertionsDsl {
    fun add(child: JMeterElement)

    fun responseAssertion(block: ResponseAssertionBuilder.() -> Unit) =
        add(ResponseAssertionBuilder().apply(block).build())

    fun jsr223Assertion(block: Jsr223AssertionBuilder.() -> Unit) =
        add(Jsr223AssertionBuilder().apply(block).build())
}
