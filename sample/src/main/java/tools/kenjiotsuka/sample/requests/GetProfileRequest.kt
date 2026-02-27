package tools.kenjiotsuka.sample.requests

import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequestBuilder

/**
 * GET /api/users/{userId}/profile — fetches a user profile with a Bearer token header.
 *
 * Both the path and the Authorization header use multi-dollar string interpolation (`$$"..."`)
 * to embed JMeter variable references like `${userId}` and `${token}` without backslash escaping.
 *
 * Type: [HttpRequestBuilder] extension lambda.
 * Usage inside a thread group: `httpRequest(getProfileRequest)`
 */
val getProfileRequest: HttpRequestBuilder.() -> Unit = {
    name = "GET api/profile"
    serverNameOrIp = "example.com"
    protocol = HttpRequest.Protocol.HTTPS
    httpRequestMethod = HttpRequest.Method.GET
    path = $$"/api/users/${userId}/profile"
    httpHeaderManager {
        header("Authorization", $$"Bearer ${token}")
    }
}
