package tools.kenjiotsuka.jmeterk.model.configelement

import tools.kenjiotsuka.jmeterk.model.core.JMeterElement

interface ConfigElementsDsl {
    fun add(child: JMeterElement)

    fun httpHeaderManager(block: HttpHeaderManagerBuilder.() -> Unit) =
        add(HttpHeaderManagerBuilder().apply(block).build())
}
