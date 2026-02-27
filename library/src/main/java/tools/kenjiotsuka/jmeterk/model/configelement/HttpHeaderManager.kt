package tools.kenjiotsuka.jmeterk.model.configelement

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class HttpHeaderManager(
    override val name: String,
    override val comment: String,
    val headers: List<Header>,
    override val enabled: Boolean
) : JMeterLeaf(name, comment, enabled) {

    /** A single HTTP header entry. */
    data class Header(
        val name: String,
        val value: String
    )
}

class HttpHeaderManagerBuilder : JMeterLeafBuilder<HttpHeaderManager>() {
    override var name: String = "HTTP Header Manager"
    val headers: MutableList<HttpHeaderManager.Header> = mutableListOf()

    fun header(name: String, value: String) {
        headers.add(HttpHeaderManager.Header(name, value))
    }

    override fun doBuild(): HttpHeaderManager = HttpHeaderManager(
        name = name,
        comment = comment,
        headers = headers.toList(),
        enabled = enabled
    )
}
