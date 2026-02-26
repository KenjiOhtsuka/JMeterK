package tools.kenjiotsuka.jmeterk

import tools.kenjiotsuka.jmeterk.model.thread.threadGroup

fun main(vararg args: String) {
    println(1)

    val a = threadGroup {
        threadGroup {

        }
    }
    println(a)
}