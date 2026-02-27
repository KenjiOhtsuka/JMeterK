package tools.kenjiotsuka.sample.threads

import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroup
import tools.kenjiotsuka.jmeterk.model.thread.ThreadGroupBuilder
import tools.kenjiotsuka.sample.requests.getHomepageRequest
import tools.kenjiotsuka.sample.requests.getProfileRequest
import tools.kenjiotsuka.sample.requests.postLoginRequest

/**
 * Demonstrates defining a thread group as a [ThreadGroup] object (eagerly built at definition time).
 *
 * Usage in testPlan {}: `add(aThreadGroup)`
 *
 * This approach is straightforward when the thread group configuration is fixed and
 * does not need to be reused as a template.
 *
 * The individual requests ([getHomepageRequest], [postLoginRequest], [getProfileRequest])
 * are defined in the `requests` package and shared with [anotherThreadGroup].
 */
val aThreadGroup: ThreadGroup = ThreadGroupBuilder().apply {
    name = "Users"
    numberOfThreads = 10
    rampUpPeriodTime = 5

    httpRequest(getHomepageRequest)
    httpRequest(postLoginRequest)
    httpRequest(getProfileRequest)
}.build()
