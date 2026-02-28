package tools.kenjiotsuka.jmeterk.model.postprocessor

import tools.kenjiotsuka.jmeterk.model.core.JMeterElement

interface PostProcessorsDsl {
    fun add(child: JMeterElement)

    fun jsr223PostProcessor(block: Jsr223PostProcessorBuilder.() -> Unit) =
        add(Jsr223PostProcessorBuilder().apply(block).build())

    fun cssSelectorExtractor(block: CssSelectorExtractorBuilder.() -> Unit) =
        add(CssSelectorExtractorBuilder().apply(block).build())

    fun jsonExtractor(block: JsonExtractorBuilder.() -> Unit) =
        add(JsonExtractorBuilder().apply(block).build())
}
