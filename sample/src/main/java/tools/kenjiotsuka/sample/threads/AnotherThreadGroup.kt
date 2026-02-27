package tools.kenjiotsuka.sample.threads

import tools.kenjiotsuka.jmeterk.model.core.TestPlanBuilder
import tools.kenjiotsuka.sample.requests.getHomepageRequest
import tools.kenjiotsuka.sample.requests.getProfileRequest
import tools.kenjiotsuka.sample.requests.postLoginRequest

/**
 * Demonstrates defining thread group(s) as a [TestPlanBuilder] extension lambda (evaluated at use time).
 *
 * Usage in testPlan {}: `anotherThreadGroup()`
 *
 * This approach allows a single lambda to add multiple thread groups at once,
 * making it useful for grouping related thread groups into a reusable block.
 *
 * The individual requests ([getHomepageRequest], [postLoginRequest], [getProfileRequest])
 * are defined in the `requests` package and shared with [aThreadGroup].
 */
val anotherThreadGroup: TestPlanBuilder.() -> Unit = {
    threadGroup {
        name = "Other Users"
        numberOfThreads = 5
        rampUpPeriodTime = 2

        httpRequest(getHomepageRequest)
        httpRequest(postLoginRequest)
        httpRequest(getProfileRequest)
    }
}
