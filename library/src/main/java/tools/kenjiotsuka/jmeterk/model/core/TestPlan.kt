package tools.kenjiotsuka.jmeterk.model.core

import tools.kenjiotsuka.jmeterk.model.thread.ThreadsDsl


class TestPlan(
    override val name: String,
    override val comment: String,
    val functionalMode: Boolean,
    val serializeThreadGroups: Boolean,
    override val enabled: Boolean
): JMeterContainer(name, comment, enabled)

class TestPlanBuilder : JMeterContainerBuilder<TestPlan>(), ThreadsDsl {
    override var name: String = "Test Plan"
    var functionalMode: Boolean = false
    var serializeThreadGroups: Boolean = false

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