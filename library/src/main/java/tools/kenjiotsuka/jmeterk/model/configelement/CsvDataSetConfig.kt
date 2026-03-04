package tools.kenjiotsuka.jmeterk.model.configelement

import tools.kenjiotsuka.jmeterk.model.core.JMeterLeaf
import tools.kenjiotsuka.jmeterk.model.core.JMeterLeafBuilder

data class CsvDataSetConfig(
    override val name: String,
    override val comment: String,
    /** GUI: "Filename". JMX: `filename`. */
    val filename: String,
    /**
     * GUI: "File encoding" dropdown. JMX: `fileEncoding`.
     * [FileEncoding.DEFAULT] emits an empty string (JMeter uses platform default encoding).
     * [FileEncoding.EDIT] uses [customFileEncoding] for the JMX value.
     */
    val fileEncoding: FileEncoding,
    /**
     * Custom file encoding string used when [fileEncoding] is [FileEncoding.EDIT].
     * Must be `null` for all other [FileEncoding] values.
     */
    val customFileEncoding: String?,
    /**
     * GUI: "Variable Names (comma delimited)". JMX: `variableNames` (comma-separated string).
     * Empty list = blank (JMeter auto-detects from the first line if [ignoreFirstLine] is false).
     */
    val variableNames: List<String>,
    /**
     * GUI: "Ignore first line". JMX: `ignoreFirstLine` (boolProp, or stringProp when [customIgnoreFirstLine] is set).
     * Default: false.
     */
    val ignoreFirstLine: Boolean,
    /**
     * JMeter variable/function expression overriding [ignoreFirstLine]. JMX: `ignoreFirstLine` (stringProp).
     * When non-blank, this value is emitted as-is instead of the boolean. Example: `"\${__P(csv.ignoreFirst,false)}"`.
     * When blank or null, [ignoreFirstLine] is used.
     */
    val customIgnoreFirstLine: String?,
    /** GUI: "Delimiter (use '\t' for tab)". JMX: `delimiter`. Default: `","`. */
    val delimiter: String,
    /**
     * GUI: "Allow quoted data?" JMX: `quotedData` (boolProp, or stringProp when [customAllowQuotedData] is set).
     * Default: false.
     */
    val allowQuotedData: Boolean,
    /**
     * JMeter variable/function expression overriding [allowQuotedData]. JMX: `quotedData` (stringProp).
     * When non-blank, this value is emitted as-is instead of the boolean.
     * When blank or null, [allowQuotedData] is used.
     */
    val customAllowQuotedData: String?,
    /**
     * GUI: "Recycle on EOF?" JMX: `recycle` (boolProp, or stringProp when [customRecycleOnEof] is set).
     * Default: true.
     */
    val recycleOnEof: Boolean,
    /**
     * JMeter variable/function expression overriding [recycleOnEof]. JMX: `recycle` (stringProp).
     * When non-blank, this value is emitted as-is instead of the boolean.
     * When blank or null, [recycleOnEof] is used.
     */
    val customRecycleOnEof: String?,
    /**
     * GUI: "Stop thread on EOF?" JMX: `stopThread` (boolProp, or stringProp when [customStopThreadOnEof] is set).
     * Default: false.
     */
    val stopThreadOnEof: Boolean,
    /**
     * JMeter variable/function expression overriding [stopThreadOnEof]. JMX: `stopThread` (stringProp).
     * When non-blank, this value is emitted as-is instead of the boolean.
     * When blank or null, [stopThreadOnEof] is used.
     */
    val customStopThreadOnEof: String?,
    /** GUI: "Sharing mode" dropdown. JMX: `shareMode`. Default: [ShareMode.ALL]. */
    val shareMode: ShareMode,
    override val enabled: Boolean,
) : JMeterLeaf(name, comment, enabled) {

    /**
     * GUI: "File encoding" dropdown choices. JMX: `fileEncoding` stringProp.
     * Choose [EDIT] and set [CsvDataSetConfigBuilder.customFileEncoding] for a custom encoding name.
     */
    enum class FileEncoding(val jmxValue: String?) {
        /** GUI: blank (no selection). JMX: `""`. JMeter uses the platform default encoding. */
        DEFAULT(""),
        /** GUI: `"UTF-8"`. JMX: `"UTF-8"`. */
        UTF_8("UTF-8"),
        /** GUI: `"UTF-16"`. JMX: `"UTF-16"`. */
        UTF_16("UTF-16"),
        /** GUI: `"ISO-8859-15"`. JMX: `"ISO-8859-15"`. */
        ISO_8859_15("ISO-8859-15"),
        /** GUI: `"US-ASCII"`. JMX: `"US-ASCII"`. */
        US_ASCII("US-ASCII"),
        /**
         * GUI: "Edit" - allows free-text input.
         * Set [CsvDataSetConfigBuilder.customFileEncoding] to any valid Java charset name
         * (e.g. `"Shift_JIS"`, `"EUC-JP"`). JMX: the value of [CsvDataSetConfigBuilder.customFileEncoding].
         */
        EDIT(null),
    }

    /** GUI: "Sharing mode" dropdown choices. JMX: `shareMode` stringProp. */
    enum class ShareMode(val jmxValue: String) {
        /** GUI: "All threads". JMX: `"shareMode.all"`. */
        ALL("shareMode.all"),
        /** GUI: "Current thread group". JMX: `"shareMode.group"`. */
        CURRENT_THREAD_GROUP("shareMode.group"),
        /** GUI: "Current thread". JMX: `"shareMode.thread"`. */
        CURRENT_THREAD("shareMode.thread"),
    }
}

/**
 * Builder for [CsvDataSetConfig] - maps to the JMeter "CSV Data Set Config" element.
 *
 * Minimal example (reads `data.csv` with comma delimiter and auto-detected variable names):
 * ```kotlin
 * csvDataSetConfig {
 *     filename = "data.csv"
 * }
 * ```
 *
 * Example with explicit variable names and custom encoding:
 * ```kotlin
 * csvDataSetConfig {
 *     filename           = "users.csv"
 *     fileEncoding       = CsvDataSetConfig.FileEncoding.EDIT
 *     customFileEncoding = "Shift_JIS"
 *     variableNames      = listOf("username", "password")
 *     ignoreFirstLine    = true
 *     recycleOnEof       = false
 *     stopThreadOnEof    = true
 *     shareMode          = CsvDataSetConfig.ShareMode.CURRENT_THREAD_GROUP
 * }
 * ```
 *
 * Example using JMeter property expressions for runtime control:
 * ```kotlin
 * csvDataSetConfig {
 *     filename              = "data.csv"
 *     customRecycleOnEof    = "\${__P(csv.recycle,true)}"
 *     customStopThreadOnEof = "\${__P(csv.stop,false)}"
 * }
 * ```
 */
class CsvDataSetConfigBuilder : JMeterLeafBuilder<CsvDataSetConfig>() {
    /** Display name shown in the JMeter GUI tree. Default: `"CSV Data Set Config"`. */
    override var name: String = "CSV Data Set Config"

    /** GUI: "Filename". Path to the CSV file (absolute or relative to JMeter's working directory). */
    var filename: String = ""

    /**
     * GUI: "File encoding" dropdown.
     * Use [CsvDataSetConfig.FileEncoding.DEFAULT] (empty string) to let JMeter use the platform encoding.
     * Use [CsvDataSetConfig.FileEncoding.EDIT] and set [customFileEncoding] for a custom charset.
     */
    var fileEncoding: CsvDataSetConfig.FileEncoding = CsvDataSetConfig.FileEncoding.DEFAULT

    /**
     * Custom charset name used when [fileEncoding] is [CsvDataSetConfig.FileEncoding.EDIT].
     * Must be a valid Java charset name (e.g. `"Shift_JIS"`, `"EUC-JP"`, `"Windows-1252"`).
     * Must be `null` (or left unset) when [fileEncoding] is any value other than [CsvDataSetConfig.FileEncoding.EDIT].
     * Must be non-blank when [fileEncoding] is [CsvDataSetConfig.FileEncoding.EDIT].
     */
    var customFileEncoding: String? = null

    /**
     * GUI: "Variable Names (comma delimited)".
     * Names of JMeter variables populated from each CSV row, in column order.
     * Leave empty to have JMeter read variable names from the first line of the file.
     */
    var variableNames: List<String> = emptyList()

    /**
     * GUI: "Ignore first line (only used if Variable Names is not empty)".
     * When `true`, the first line of the CSV file is skipped (treated as a header).
     * Applies only when [variableNames] is non-empty. Default: `false`.
     * Set [customIgnoreFirstLine] instead if you need a JMeter variable or function expression.
     */
    var ignoreFirstLine: Boolean = false

    /**
     * JMeter variable/function expression for "Ignore first line". When non-blank, takes priority
     * over [ignoreFirstLine] and is serialized as a `stringProp` (instead of `boolProp`).
     * Typical use: `"\${__P(csv.ignoreFirst,false)}"` to control the value via a JMeter property.
     */
    var customIgnoreFirstLine: String? = null

    /**
     * GUI: "Delimiter (use '\t' for tab)".
     * The character separating columns in each CSV line. Default: `","`.
     * Use `"\t"` for tab-delimited files.
     */
    var delimiter: String = ","

    /**
     * GUI: "Allow quoted data?"
     * When `true`, values may be enclosed in double-quotes and may contain the delimiter character.
     * Default: `false`.
     * Set [customAllowQuotedData] instead if you need a JMeter variable or function expression.
     */
    var allowQuotedData: Boolean = false

    /**
     * JMeter variable/function expression for "Allow quoted data". When non-blank, takes priority
     * over [allowQuotedData] and is serialized as a `stringProp`.
     * Typical use: `"\${__P(csv.quoted,false)}"`.
     */
    var customAllowQuotedData: String? = null

    /**
     * GUI: "Recycle on EOF?"
     * When `true`, JMeter loops back to the beginning of the file after the last row.
     * Default: `true`.
     * Set [customRecycleOnEof] instead if you need a JMeter variable or function expression.
     */
    var recycleOnEof: Boolean = true

    /**
     * JMeter variable/function expression for "Recycle on EOF". When non-blank, takes priority
     * over [recycleOnEof] and is serialized as a `stringProp`.
     * Typical use: `"\${__P(csv.recycle,true)}"`.
     */
    var customRecycleOnEof: String? = null

    /**
     * GUI: "Stop thread on EOF?"
     * When `true`, the thread stops when it reaches the end of the file.
     * Only meaningful when [recycleOnEof] is `false`. Default: `false`.
     * Set [customStopThreadOnEof] instead if you need a JMeter variable or function expression.
     */
    var stopThreadOnEof: Boolean = false

    /**
     * JMeter variable/function expression for "Stop thread on EOF". When non-blank, takes priority
     * over [stopThreadOnEof] and is serialized as a `stringProp`.
     * Typical use: `"\${__P(csv.stop,false)}"`.
     */
    var customStopThreadOnEof: String? = null

    /**
     * GUI: "Sharing mode" dropdown.
     * Controls how the CSV file cursor is shared across threads. Default: [CsvDataSetConfig.ShareMode.ALL].
     * - [CsvDataSetConfig.ShareMode.ALL]: all threads share one cursor (each row delivered once globally).
     * - [CsvDataSetConfig.ShareMode.CURRENT_THREAD_GROUP]: each thread group has its own cursor.
     * - [CsvDataSetConfig.ShareMode.CURRENT_THREAD]: each thread has its own independent cursor.
     */
    var shareMode: CsvDataSetConfig.ShareMode = CsvDataSetConfig.ShareMode.ALL

    override fun doBuild(): CsvDataSetConfig = CsvDataSetConfig(
        name = name,
        comment = comment,
        filename = filename,
        fileEncoding = fileEncoding,
        customFileEncoding = customFileEncoding,
        variableNames = variableNames,
        ignoreFirstLine = ignoreFirstLine,
        customIgnoreFirstLine = customIgnoreFirstLine,
        delimiter = delimiter,
        allowQuotedData = allowQuotedData,
        customAllowQuotedData = customAllowQuotedData,
        recycleOnEof = recycleOnEof,
        customRecycleOnEof = customRecycleOnEof,
        stopThreadOnEof = stopThreadOnEof,
        customStopThreadOnEof = customStopThreadOnEof,
        shareMode = shareMode,
        enabled = enabled,
    )

    override fun checkBuilt(element: CsvDataSetConfig) {
        if (fileEncoding == CsvDataSetConfig.FileEncoding.EDIT) {
            require(!customFileEncoding.isNullOrBlank()) {
                "customFileEncoding must be a non-blank charset name when fileEncoding is EDIT"
            }
        } else {
            require(customFileEncoding == null) {
                "customFileEncoding must be null when fileEncoding is not EDIT (got: fileEncoding=$fileEncoding)"
            }
        }
    }
}
