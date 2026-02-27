package tools.kenjiotsuka.jmeterk.model.thread

enum class ActionToBeTakenAfterSampleError(val jmxValue: String) {
    CONTINUE("continue"),
    START_NEXT_THREAD_LOOP("startnextloop"),
    STOP_THREAD("stopthread"),
    STOP_TEST("stoptest"),
    STOP_TEST_NOW("stoptestnow")
}
