package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.configelement.CsvDataSetConfig

fun CsvDataSetConfig.toJmxNode(): JmxElement = JmxElement(
    tag = "CSVDataSet",
    attributes = buildMap {
        put("guiclass", "TestBeanGUI")
        put("testclass", "CSVDataSet")
        put("testname", name)
        if (!enabled) put("enabled", "false")
    },
    children = buildList {
        if (comment.isNotEmpty()) add(stringProp("TestPlan.comments", comment))
        add(stringProp("delimiter", delimiter))
        add(stringProp("fileEncoding",
            if (fileEncoding == CsvDataSetConfig.FileEncoding.EDIT) customFileEncoding!! else fileEncoding.jmxValue ?: ""
        ))
        add(stringProp("filename", filename))
        add(boolOrStringProp("ignoreFirstLine", ignoreFirstLine, customIgnoreFirstLine))
        add(boolOrStringProp("quotedData", allowQuotedData, customAllowQuotedData))
        add(boolOrStringProp("recycle", recycleOnEof, customRecycleOnEof))
        add(stringProp("shareMode", shareMode.jmxValue))
        add(boolOrStringProp("stopThread", stopThreadOnEof, customStopThreadOnEof))
        add(stringProp("variableNames", variableNames.joinToString(",")))
    }
)

/** Emits a [stringProp] when [customValue] is non-blank, otherwise a [boolProp]. */
private fun boolOrStringProp(name: String, boolValue: Boolean, customValue: String?): JmxNode =
    if (!customValue.isNullOrBlank()) stringProp(name, customValue) else boolProp(name, boolValue)
