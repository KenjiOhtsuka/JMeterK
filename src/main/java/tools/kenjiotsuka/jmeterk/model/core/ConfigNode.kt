package tools.kenjiotsuka.jmeterk.model.core

data class ConfigNode(
    val tagName: String,
    val attributes: Map<String, String> = emptyMap(),
    val value: String? = null,
    val configNodes: List<ConfigNode> = emptyList()
)
