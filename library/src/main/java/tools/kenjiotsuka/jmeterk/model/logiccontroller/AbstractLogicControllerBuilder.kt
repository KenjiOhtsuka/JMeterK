package tools.kenjiotsuka.jmeterk.model.logiccontroller

import tools.kenjiotsuka.jmeterk.model.assertion.AssertionsDsl
import tools.kenjiotsuka.jmeterk.model.configelement.ConfigElementsDsl
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainerBuilder
import tools.kenjiotsuka.jmeterk.model.sampler.SamplersDsl

abstract class AbstractLogicControllerBuilder<T : JMeterContainer> :
    JMeterContainerBuilder<T>(),
    LogicControllersDsl,
    SamplersDsl,
    ConfigElementsDsl,
    AssertionsDsl
