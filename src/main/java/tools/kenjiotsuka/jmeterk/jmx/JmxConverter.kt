package tools.kenjiotsuka.jmeterk.jmx

class JmxConverter {
    fun convert(node: JmxNode): String = when (node) {
        is JmxElement  -> convertElement(node)
        is JmxText     -> escape(node.text)
        is JmxHashTree -> if (node.children.isEmpty()) "<hashTree/>"
                          else "<hashTree>${node.children.joinToString("") { convert(it) }}</hashTree>"
    }

    private fun convertElement(el: JmxElement): String {
        val attrStr = el.attributes.entries.joinToString(" ") { (k, v) -> """$k="${escape(v)}"""" }
            .let { if (it.isNotEmpty()) " $it" else "" }
        return if (el.children.isEmpty()) {
            "<${el.tag}$attrStr/>"
        } else {
            val body = el.children.joinToString("") { convert(it) }
            "<${el.tag}$attrStr>$body</${el.tag}>"
        }
    }

    private fun escape(text: String): String = text
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
}
