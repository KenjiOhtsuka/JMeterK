package tools.kenjiotsuka.jmeterk.model.preprocessor

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class UserParameters(
    override val name: String,
    override val comment: String,
    /** Update Once Per Iteration */
    val perIteration: Boolean,
    /** Each entry represents one variable row in the table: its name and one value per thread. */
    val variables: List<Variable>,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled) {

    data class Variable(
        val name: String,
        /** Values in thread order (Thread 1, Thread 2, …). */
        val values: List<String>
    )
}

class UserParametersBuilder : JMeterLeafBuilder<UserParameters>() {
    override var name: String = "User Parameters"
    var perIteration: Boolean = false
    private val variables: MutableList<UserParameters.Variable> = mutableListOf()

    /** Adds a variable row with the given [name] and per-thread [values]. */
    fun variable(name: String, vararg values: String) {
        variables.add(UserParameters.Variable(name, values.toList()))
    }

    override fun doBuild(): UserParameters = UserParameters(
        name = name,
        comment = comment,
        perIteration = perIteration,
        variables = variables.toList(),
        enabled = enabled
    )
}
