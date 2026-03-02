package tools.kenjiotsuka.jmeterk.model.timer

import tools.kenjiotsuka.jmeterk.model.core.JMeterElement

interface TimersDsl {
    fun add(child: JMeterElement)

    fun constantTimer(block: ConstantTimerBuilder.() -> Unit) =
        add(ConstantTimerBuilder().apply(block).build())

    fun uniformRandomTimer(block: UniformRandomTimerBuilder.() -> Unit) =
        add(UniformRandomTimerBuilder().apply(block).build())

    fun preciseThroughputTimer(block: PreciseThroughputTimerBuilder.() -> Unit) =
        add(PreciseThroughputTimerBuilder().apply(block).build())

    fun constantThroughputTimer(block: ConstantThroughputTimerBuilder.() -> Unit) =
        add(ConstantThroughputTimerBuilder().apply(block).build())
}
