package tools.kenjiotsuka.jmeterk.model.sampler

import tools.kenjiotsuka.jmeterk.model.assertion.AssertionsDsl
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainerBuilder

data class DebugSampler(
    override val name: String,
    override val comment: String,
    /** Display JMeter properties in the sample result. */
    val displayJMeterProperties: Boolean,
    /** Display JMeter variables in the sample result. */
    val displayJMeterVariables: Boolean,
    /** Display system properties in the sample result. */
    val displaySystemProperties: Boolean,
    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled)

class DebugSamplerBuilder : JMeterContainerBuilder<DebugSampler>(), AssertionsDsl {
    override var name: String = "Debug Sampler"
    /** Show JMeter properties (e.g. jmeter.version) in results. Default: false. */
    var displayJMeterProperties: Boolean = false
    /** Show JMeter variables (e.g. \${myVar}) in results. Default: false. */
    var displayJMeterVariables: Boolean = false
    /** Show JVM system properties in results. Default: false. */
    var displaySystemProperties: Boolean = false

    override fun doBuild(): DebugSampler = DebugSampler(
        name = name,
        comment = comment,
        displayJMeterProperties = displayJMeterProperties,
        displayJMeterVariables = displayJMeterVariables,
        displaySystemProperties = displaySystemProperties,
        enabled = enabled
    )
}
