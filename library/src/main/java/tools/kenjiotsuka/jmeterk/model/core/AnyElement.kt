package tools.kenjiotsuka.jmeterk.model.core

class AnyElement(
    override val name: String,
    override val comment: String,
    val tagName: String,
    val attributes: Map<String, String>,
    val value: String?,
    val configNodes: List<ConfigNode>,
    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled)

class AnyElementBuilder : JMeterContainerBuilder<AnyElement>() {
    override var name: String = ""
    var tagName: String = ""
    var attributes: Map<String, String> = emptyMap()
    var value: String? = null
    private val configNodes: MutableList<ConfigNode> = mutableListOf()

    fun configNode(block: ConfigNodeBuilder.() -> Unit) {
        configNodes.add(ConfigNodeBuilder().apply(block).build())
    }

    override fun doBuild(): AnyElement =
        AnyElement(name, comment, tagName, attributes.toMap(), value, configNodes.toList(), enabled)
}

class ConfigNodeBuilder {
    var tagName: String = ""
    var attributes: Map<String, String> = emptyMap()
    var value: String? = null
    private val configNodes: MutableList<ConfigNode> = mutableListOf()

    fun configNode(block: ConfigNodeBuilder.() -> Unit) {
        configNodes.add(ConfigNodeBuilder().apply(block).build())
    }

    fun build(): ConfigNode = ConfigNode(tagName, attributes.toMap(), value, configNodes.toList())
}
