package tools.kenjiotsuka.jmeterk.model.thread

import tools.kenjiotsuka.jmeterk.model.core.JMeterElement

interface ThreadsDsl {
    fun add(child: JMeterElement)

    fun threadGroup(block: ThreadGroupBuilder.() -> Unit) =
        add(ThreadGroupBuilder().apply(block).build())

    fun openModelThreadGroup(block: OpenModelThreadGroupBuilder.() -> Unit) =
        add(OpenModelThreadGroupBuilder().apply(block).build())
}
