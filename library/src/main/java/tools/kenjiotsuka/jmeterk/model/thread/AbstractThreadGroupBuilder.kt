package tools.kenjiotsuka.jmeterk.model.thread

import tools.kenjiotsuka.jmeterk.model.assertion.AssertionsDsl
import tools.kenjiotsuka.jmeterk.model.configelement.ConfigElementsDsl
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainerBuilder
import tools.kenjiotsuka.jmeterk.model.logiccontroller.LogicControllersDsl
import tools.kenjiotsuka.jmeterk.model.postprocessor.PostProcessorsDsl
import tools.kenjiotsuka.jmeterk.model.preprocessor.PreProcessorsDsl
import tools.kenjiotsuka.jmeterk.model.sampler.SamplersDsl

abstract class AbstractThreadGroupBuilder<T : JMeterContainer> :
    JMeterContainerBuilder<T>(),
    LogicControllersDsl,
    SamplersDsl,
    ConfigElementsDsl,
    AssertionsDsl,
    PreProcessorsDsl,
    PostProcessorsDsl
