package tools.kenjiotsuka.jmeterk.model.thread

enum class ActionToBeTakenAfterSampleError {
    CONTINUE,
    START_NEXT_THREAD_LOOP,
    STOP_THREAD,
    STOP_TEST,
    STOP_TEST_NOW
}
