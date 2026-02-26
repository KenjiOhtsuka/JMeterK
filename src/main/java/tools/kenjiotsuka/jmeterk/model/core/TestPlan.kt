package tools.kenjiotsuka.jmeterk.model.core

import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroupBuilder


class TestPlan(
    override val name: String,
    override val comment: String,
    override val enabled: Boolean
): JMeterContainer(name, comment, enabled)

class TestPlanBuilder : JMeterContainerBuilder<TestPlan>() {
    override var name: String = "Test Plan"

    override fun doBuild(): TestPlan {
        val tp = TestPlan(
            name = name,
            comment = comment,
            enabled = enabled
        )
        children.forEach { tp.add(it) }
        return tp
    }
}

fun testPlan(block: ThreadGroupBuilder.() -> Unit): JMeterContainer {
    return ThreadGroupBuilder().apply(block).build()
}