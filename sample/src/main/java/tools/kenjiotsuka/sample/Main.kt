package tools.kenjiotsuka.sample

import tools.kenjiotsuka.jmeterk.jmx.buildJmxDocument
import tools.kenjiotsuka.jmeterk.model.core.testPlan
import tools.kenjiotsuka.sample.threads.aThreadGroup
import tools.kenjiotsuka.sample.threads.anotherThreadGroup
import java.io.File

/**
 * Sample: build a JMeter test plan and write it to a .jmx file.
 *
 * Demonstrates multi-dollar string interpolation (Kotlin 2.2+) for writing
 * JMeter variable references like ${username} without backslash escaping.
 *
 * Run with: ./gradlew :sample:run
 */
fun main() {
    val plan = testPlan {
        name = "Sample Test Plan"
        add(aThreadGroup)    // ThreadGroup object — defined in AThreadGroup.kt
        anotherThreadGroup() // TestPlanBuilder lambda — defined in AnotherThreadGroup.kt
    }

    val jmx = buildJmxDocument(plan)

    // Print to stdout
    println(jmx)

    // Optionally write to file
    val output = File("sample-output.jmx")
    output.writeText(jmx)
    println("\nWritten to: ${output.absolutePath}")
}
