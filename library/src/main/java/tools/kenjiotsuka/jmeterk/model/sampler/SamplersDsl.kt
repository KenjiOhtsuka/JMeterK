package tools.kenjiotsuka.jmeterk.model.sampler

import tools.kenjiotsuka.jmeterk.model.core.JMeterElement

interface SamplersDsl {
    fun add(child: JMeterElement)

    fun httpRequest(block: HttpRequestBuilder.() -> Unit) =
        add(HttpRequestBuilder().apply(block).build())

    fun debugSampler(block: DebugSamplerBuilder.() -> Unit) =
        add(DebugSamplerBuilder().apply(block).build())
    fun flowControlAction(block: FlowControlActionBuilder.() -> Unit) =
        add(FlowControlActionBuilder().apply(block).build())
}
