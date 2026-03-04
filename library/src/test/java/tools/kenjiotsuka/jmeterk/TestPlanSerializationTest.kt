package tools.kenjiotsuka.jmeterk

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tools.kenjiotsuka.jmeterk.jmx.buildJmxDocument
import tools.kenjiotsuka.jmeterk.model.core.testPlan

class TestPlanSerializationTest {

    /**
     * Builds the same structure as testfile/test.jmx and verifies the serialized output matches.
     *
     * Normalization: strips newline-based indentation whitespace between tags while preserving
     * meaningful text content (e.g. the single space in Argument.value).
     */
    @Test
    fun `serialized DSL output matches test jmx`() {
        val plan = testPlan {
            name = "Test Plan"
            httpRequestThreadGroup()
            debugSamplerThreadGroup()
            openModelThreadGroupSetup()
            configManagerThreadGroup()
            preProcessorThreadGroup()
            postProcessorThreadGroup()
            timerThreadGroup()
            listenerThreadGroup()
            samplerThreadGroup()
        }

        val actual = normalizeXml(buildJmxDocument(plan))
        val expected = normalizeXml(loadResource("test.jmx"))

        assertEquals(expected, actual)
    }

    private fun loadResource(name: String): String =
        javaClass.classLoader.getResourceAsStream(name)!!.bufferedReader().readText()

    /**
     * Strips newline-based indentation (newline + optional spaces/tabs between tags) so that
     * compact and indented XML representations can be compared structurally.
     * Single spaces inside text nodes (e.g. Argument.value = " ") are preserved.
     */
    private fun normalizeXml(xml: String): String =
        xml.replace(Regex(">[\r\n][ \t]*<"), "><")
           .replace(Regex("""\s+enabled="true""""), "")
           // Strip numeric name attributes (JMeter-internal hashCode IDs on collectionProp/stringProp)
           .replace(Regex(""" name="-?\d+""""), "")
           .trim()
}

