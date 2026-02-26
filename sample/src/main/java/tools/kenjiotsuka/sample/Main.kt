package tools.kenjiotsuka.sample

import tools.kenjiotsuka.jmeterk.jmx.buildJmxDocument
import tools.kenjiotsuka.jmeterk.model.assertion.ResponseAssertion
import tools.kenjiotsuka.jmeterk.model.core.testPlan
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest
import java.io.File

/**
 * Sample: build a JMeter test plan and write it to a .jmx file.
 *
 * Run with: ./gradlew :sample:run
 */
fun main() {
    val plan = testPlan {
        name = "Sample Test Plan"

        threadGroup {
            name = "Users"
            numberOfThreads = 10
            rampUpPeriodTime = 5

            httpRequest {
                name = "GET example.com"
                serverNameOrIp = "example.com"
                protocol = HttpRequest.Protocol.HTTPS
                httpRequestMethod = HttpRequest.Method.GET
                path = "/"

                responseAssertion {
                    name = "Status 200"
                    fieldToTest = ResponseAssertion.FieldToTest.RESPONSE_CODE
                    matchingRule = ResponseAssertion.PatternMatchingRule.EQUALS
                    patterns.add("200")
                }
            }

            httpRequest {
                name = "POST api/login"
                serverNameOrIp = "example.com"
                protocol = HttpRequest.Protocol.HTTPS
                httpRequestMethod = HttpRequest.Method.POST
                path = "/api/login"
                bodyData = """{"username":"user","password":"pass"}"""
            }
        }
    }

    val jmx = buildJmxDocument(plan)

    // Print to stdout
    println(jmx)

    // Optionally write to file
    val output = File("sample-output.jmx")
    output.writeText(jmx)
    println("\nWritten to: ${output.absolutePath}")
}
