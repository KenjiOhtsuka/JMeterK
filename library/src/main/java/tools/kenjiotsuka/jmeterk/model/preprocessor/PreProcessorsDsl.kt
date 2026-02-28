package tools.kenjiotsuka.jmeterk.model.preprocessor

import tools.kenjiotsuka.jmeterk.model.core.JMeterElement

interface PreProcessorsDsl {
    fun add(child: JMeterElement)

    fun jsr223PreProcessor(block: Jsr223PreProcessorBuilder.() -> Unit) =
        add(Jsr223PreProcessorBuilder().apply(block).build())

    fun userParameters(block: UserParametersBuilder.() -> Unit) =
        add(UserParametersBuilder().apply(block).build())
}
