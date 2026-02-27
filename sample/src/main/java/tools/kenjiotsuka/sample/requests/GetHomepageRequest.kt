package tools.kenjiotsuka.sample.requests

import tools.kenjiotsuka.jmeterk.model.assertion.ResponseAssertion
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequestBuilder

/**
 * GET / — verifies a 200 response code.
 *
 * Type: [HttpRequestBuilder] extension lambda.
 * Usage inside a thread group: `httpRequest(getHomepageRequest)`
 */
val getHomepageRequest: HttpRequestBuilder.() -> Unit = {
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
