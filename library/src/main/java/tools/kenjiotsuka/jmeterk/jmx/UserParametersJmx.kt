package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.preprocessor.UserParameters

fun UserParameters.toJmxNode(): JmxElement {
    val threadCount = variables.maxOfOrNull { it.values.size } ?: 0

    val namesProp = collectionProp(
        "UserParameters.names",
        variables.map { stringProp(it.name.hashCode().toString(), it.name) }
    )

    // thread_values: one inner collectionProp per thread (column), each has one value per variable (row)
    val threadValuesProp = collectionProp(
        "UserParameters.thread_values",
        (0 until threadCount).map { threadIndex ->
            val vals = variables.map { it.values.getOrElse(threadIndex) { "" } }
            collectionProp(
                vals.hashCode().toString(),
                vals.map { stringProp(it.hashCode().toString(), it) }
            )
        }
    )

    return JmxElement(
        tag = "UserParameters",
        attributes = buildMap {
            put("guiclass", "UserParametersGui")
            put("testclass", "UserParameters")
            put("testname", name)
            if (!enabled) put("enabled", "false")
        },
        children = buildList {
            add(namesProp)
            add(threadValuesProp)
            add(boolProp("UserParameters.per_iteration", perIteration))
            if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        }
    )
}
