package tools.kenjiotsuka.jmeterk.jmx

import java.io.File

class JmxWriter(private val converter: JmxConverter = JmxConverter()) {
    fun write(node: JmxNode, file: File) {
        file.writeText(converter.convert(node))
    }
}
