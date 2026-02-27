package tools.kenjiotsuka.jmeterk.model.core

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

    open fun build(): T {
        val element = doBuild()
        checkBuilt(element)
        return element
    }

    protected abstract fun doBuild(): T

    protected open fun checkBuilt(element: T) {}
}

abstract class JMeterContainerBuilder<T: JMeterContainer> : JMeterElementBuilder<T>() {
    var children: MutableList<JMeterElement> = mutableListOf()

    /**
     * Overrides [build] to wire collected [children] into the built element automatically.
     * Children are added after [doBuild] and before [validate], so validators can inspect them.
     */
    final override fun build(): T {
        val element = doBuild()
        children.forEach { element.add(it) }
        checkBuilt(element)
        return element
    }

    /**
     * Adds an [AnyElement] child using a raw XML structure.
     *
     * This is an intentional escape hatch that bypasses the category DSL constraints
     * (e.g. [TestPlanBuilder] normally only exposes [ThreadsDsl]). Use it to add JMeter elements
     * that do not yet have a typed model class, or in tests that need arbitrary nesting.
     */
    fun anyElement(block: AnyElementBuilder.() -> Unit) {
        add(AnyElementBuilder().apply(block).build())
    }

    fun add(child: JMeterElement) {
        children.add(child)
    }
}

abstract class JMeterLeafBuilder<T: JMeterLeaf> : JMeterElementBuilder<T>()

