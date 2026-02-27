package tools.kenjiotsuka.jmeterk.jmx

import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest

fun HttpRequest.toJmxNode(): JmxElement {
    val isBodyDataMode = bodyData.isNotEmpty()

    val argumentEntries = if (isBodyDataMode) {
        listOf(
            elementProp("", "HTTPArgument", listOf(
                boolProp("HTTPArgument.always_encode", false),
                stringProp("Argument.value", bodyData),
                stringProp("Argument.metadata", "=")
            ))
        )
    } else {
        parameters.map { param ->
            elementProp(param.name, "HTTPArgument", listOf(
                stringProp("Argument.name", param.name),
                stringProp("Argument.value", param.value),
                boolProp("HTTPArgument.always_encode", param.urlEncode),
                stringProp("Argument.metadata", if (param.includeEquals) "=" else ""),
                stringProp("Argument.contentType", param.contentType)
            ))
        }
    }

    val argumentsProp = if (isBodyDataMode) {
        elementProp(
            "HTTPsampler.Arguments", "Arguments",
            listOf(JmxElement("collectionProp", mapOf("name" to "Arguments.arguments"), argumentEntries))
        )
    } else {
        elementProp(
            "HTTPsampler.Arguments", "Arguments",
            "HTTPArgumentsPanel", "Arguments", "User Defined Variables",
            listOf(JmxElement("collectionProp", mapOf("name" to "Arguments.arguments"), argumentEntries))
        )
    }

    return JmxElement(
        tag = "HTTPSamplerProxy",
        attributes = buildMap {
            put("guiclass", "HttpTestSampleGui"); put("testclass", "HTTPSamplerProxy"); put("testname", name)
            if (!enabled) put("enabled", "false")
        },
        children = buildList {
            protocol?.let { add(stringProp("HTTPSampler.protocol", it.name.lowercase())) }
            serverNameOrIp?.let { add(stringProp("HTTPSampler.domain", it)) }
            portNumber?.let { add(intProp("HTTPSampler.port", it)) }
            path?.let { add(stringProp("HTTPSampler.path", it)) }
            contentEncoding?.let { add(stringProp("HTTPSampler.contentEncoding", it)) }
            add(boolProp("HTTPSampler.follow_redirects", followRedirects))
            if (redirectAutomatically) add(boolProp("HTTPSampler.auto_redirects", true))
            add(stringProp("HTTPSampler.method", httpRequestMethod.name))
            add(boolProp("HTTPSampler.use_keepalive", useKeepAlive))
            if (useMultipartFormData) add(boolProp("HTTPSampler.DO_MULTIPART_POST", true))
            if (browserCompatibleHeaders) add(boolProp("HTTPSampler.BROWSER_COMPATIBLE_MULTIPART", true))
            add(boolProp("HTTPSampler.postBodyRaw", isBodyDataMode))
            add(argumentsProp)
        }
    )
}
