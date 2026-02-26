package tools.kenjiotsuka.jmeterk

import tools.kenjiotsuka.jmeterk.model.core.testPlan

fun main(vararg args: String) {
    println(1)

    val a = testPlan {
        threadGroup {
            httpRequest {

            }
        }
    }
    println(a)
}