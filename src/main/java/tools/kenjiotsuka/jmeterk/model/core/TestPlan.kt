package tools.kenjiotsuka.jmeterk.model.core

import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroupBuilder


class TestPlan(
    override val name: String,
    override val comment: String,
    val functionalMode: Boolean,
    val serializeThreadGroups: Boolean,
    override val enabled: Boolean
): JMeterContainer(name, comment, enabled)

class TestPlanBuilder : JMeterContainerBuilder<TestPlan>() {
    override var name: String = "Test Plan"
    var functionalMode: Boolean = false
    var serializeThreadGroups: Boolean = false

    fun threadGroup(block: ThreadGroupBuilder.() -> Unit) {
        add(ThreadGroupBuilder().apply(block).build())
    }

    override fun doBuild(): TestPlan {
        return TestPlan(
            name = name,
            comment = comment,
            functionalMode = functionalMode,
            serializeThreadGroups = serializeThreadGroups,
            enabled = enabled
        )
    }
}

fun testPlan(block: TestPlanBuilder.() -> Unit): TestPlan {
    return TestPlanBuilder().apply(block).build()
}