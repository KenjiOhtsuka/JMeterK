package tools.kenjiotsuka.jmeterk.model.jsr223

/**
 * Scripting languages available in JSR223 element GUI dropdowns.
 * [scriptLanguage] is the identifier written to the JMX file.
 * Shared by [tools.kenjiotsuka.jmeterk.model.assertion.Jsr223Assertion],
 * [tools.kenjiotsuka.jmeterk.model.preprocessor.Jsr223PreProcessor],
 * [tools.kenjiotsuka.jmeterk.model.postprocessor.Jsr223PostProcessor], and
 * [tools.kenjiotsuka.jmeterk.model.sampler.Jsr223Sampler].
 */
enum class Jsr223Language(val scriptLanguage: String) {
    BEAN_SHELL("beanshell"),
    GROOVY("groovy"),
    JAVA("java"),
    JAVASCRIPT("javascript"),
    JEXL("jexl"),
    JEXL2("jexl2"),
    JEXL3("jexl3"),
    NASHORN("nashorn"),
}
