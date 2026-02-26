package tools.kenjiotsuka.jmeterk.jmx

sealed class JmxNode

data class JmxElement(
    val tag: String,
    val attributes: Map<String, String> = emptyMap(),
    val children: List<JmxNode> = emptyList()
) : JmxNode()

data class JmxText(val text: String) : JmxNode()

// Property helper functions
fun stringProp(name: String, value: String): JmxElement =
    JmxElement("stringProp", mapOf("name" to name), listOf(JmxText(value)))

fun boolProp(name: String, value: Boolean): JmxElement =
    JmxElement("boolProp", mapOf("name" to name), listOf(JmxText(value.toString())))

fun intProp(name: String, value: Int): JmxElement =
    JmxElement("intProp", mapOf("name" to name), listOf(JmxText(value.toString())))

fun longProp(name: String, value: Long): JmxElement =
    JmxElement("longProp", mapOf("name" to name), listOf(JmxText(value.toString())))

fun elementProp(
    name: String, elementType: String, guiclass: String, testclass: String,
    testname: String, enabled: Boolean, children: List<JmxNode>
) = JmxElement(
    "elementProp",
    mapOf(
        "name" to name,
        "elementType" to elementType,
        "guiclass" to guiclass,
        "testclass" to testclass,
        "testname" to testname,
        "enabled" to enabled.toString(),
    ),
    children
)