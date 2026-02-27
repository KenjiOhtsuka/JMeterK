package tools.kenjiotsuka.jmeterk.model.assertion

/**
 * GUI: "Apply to" radio button group, shared by assertion types.
 * Controls which sample results the assertion is applied to.
 */
enum class ApplyTo {
    /** Main sample and sub-samples. JMX: `Assertion.scope = "all"`. */
    MAIN_SAMPLE_AND_SUB_SAMPLES,
    /** Main sample only (default). JMX: `Assertion.scope` not emitted. */
    MAIN_SAMPLE_ONLY,
    /** Sub-samples only. JMX: `Assertion.scope = "children"`. */
    SUB_SAMPLES_ONLY,
    /** Apply to a specific JMeter variable. JMX: `Assertion.scope = "variable"` + `Scope.variable`. */
    JMETER_VARIABLE
}
