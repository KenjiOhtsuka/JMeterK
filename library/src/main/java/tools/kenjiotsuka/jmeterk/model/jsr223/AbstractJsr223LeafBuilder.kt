package tools.kenjiotsuka.jmeterk.model.jsr223

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

/**
 * Abstract builder for JSR223 script leaf elements (Assertion, PreProcessor, PostProcessor).
 * Script fields and their defaults are provided via [Jsr223ScriptDelegate].
 * For the container variant (Sampler), use the same delegation pattern with [JMeterContainerBuilder].
 */
abstract class AbstractJsr223LeafBuilder<T : JMeterLeaf>(
    private val d: Jsr223ScriptDelegate = Jsr223ScriptDelegate()
) : JMeterLeafBuilder<T>(), Jsr223ScriptBuilder by d
