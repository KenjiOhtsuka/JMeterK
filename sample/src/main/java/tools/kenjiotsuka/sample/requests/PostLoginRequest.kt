package tools.kenjiotsuka.sample.requests

import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequestBuilder

/**
 * POST /api/login — sends credentials from JMeter variables.
 *
 * Uses multi-dollar string interpolation (`$$"..."`) so that JMeter variable references
 * like `${username}` are written literally without backslash escaping.
 *
 * Type: [HttpRequestBuilder] extension lambda.
 * Usage inside a thread group: `httpRequest(postLoginRequest)`
 */
val postLoginRequest: HttpRequestBuilder.() -> Unit = {
    name = "POST api/login"
    serverNameOrIp = "example.com"
    protocol = HttpRequest.Protocol.HTTPS
    httpRequestMethod = HttpRequest.Method.POST
    path = "/api/login"
    bodyData = $$"""{"username":"${username}","password":"${password}"}"""
}
