package tools.kenjiotsuka.jmeterk.model.core

import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroupBuilder

abstract class JMeterElement(
    open val name: String,
    open val comment: String,
    open val enabled: Boolean
)

abstract class JMeterContainer(
    override val name: String,
    override val comment: String,
    override val enabled: Boolean
): JMeterElement(name, comment, enabled) {
    val children: MutableList<JMeterElement> = mutableListOf()

    fun add(child: JMeterElement) {
        children.add(child)
    }
}

abstract class JMeterLeaf(
    override val name: String,
    override val comment: String,
    override val enabled: Boolean
): JMeterElement(name, comment, enabled)

abstract class JMeterElementBuilder<T: JMeterElement>(
    var comment: String = "",
    var enabled: Boolean = true
) {
    abstract var name: String

    fun build(): T {
        val element = doBuild()
        validate(element)
        return element
    }

    protected abstract fun doBuild(): T

    protected open fun validate(element: T) {}
}

abstract class JMeterContainerBuilder<T: JMeterContainer> : JMeterElementBuilder<T>() {
    var children: MutableList<JMeterElement> = mutableListOf()
    fun add(child: JMeterElement) {
        children.add(child)
    }
}

abstract class JMeterLeafBuilder<T: JMeterLeaf> : JMeterElementBuilder<T>() {}
