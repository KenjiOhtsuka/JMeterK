package tools.kenjiotsuka.jmeterk.model.sampler

import tools.kenjiotsuka.jmeterk.model.core.JMeterContainer
import tools.kenjiotsuka.jmeterk.model.core.JMeterContainerBuilder
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest.File
import tools.kenjiotsuka.jmeterk.model.sampler.HttpRequest.Parameter

data class HttpRequest(
    override val name: String,
    override val comment: String,

    // basic attributes
    val protocol: HttpRequest.Protocol?,
    val serverNameOrIp: String?,
    val portNumber: Int?,
    val httpRequestMethod: HttpRequest.Method,
    val path: String?,
    val contentEncoding: String?,
    val redirectAutomatically: Boolean,
    val followRedirects: Boolean,
    val useKeepAlive: Boolean,
    val useMultipartFormData: Boolean,
    val browserCompatibleHeaders: Boolean,

    val parameters: Array<Parameter>,
    val bodyData: String,
    val filesUpload: Array<File>,

    // advanced attributes

    // ----

    override val enabled: Boolean
) : JMeterContainer(name, comment, enabled) {
    enum class Protocol {
        HTTPS,
        HTTP,
    }

    enum class Method {
        /** Retrieve a representation of a resource identified by a given URI. */
        GET,
        /** Submit data to be processed by a resource (e.g. form submission, file upload). */
        POST,
        /** Identical to GET but returns only HTTP headers, not the response body. */
        HEAD,
        /** Replace all current representations of a resource with the request payload. */
        PUT,
        /** Describe the communication options available for a resource. */
        OPTIONS,
        /** Perform a message loop-back test along the path to the target resource. */
        TRACE,
        /** Remove the specified resource. */
        DELETE,
        /** Apply partial modifications to a resource. */
        PATCH,
        /** Retrieve properties of a resource (WebDAV). */
        PROPFIND,
        /** Change or remove multiple properties of a resource in a single atomic act (WebDAV). */
        PROPPATCH,
        /** Create a new collection (directory) at the specified URI (WebDAV). */
        MKCOL,
        /** Copy a resource from one URI to another (WebDAV). */
        COPY,
        /** Move a resource from one URI to another (WebDAV). */
        MOVE,
        /** Lock a resource to prevent modifications by other clients (WebDAV). */
        LOCK,
        /** Unlock a previously locked resource (WebDAV). */
        UNLOCK,
        /** Request a report on a resource (WebDAV/CalDAV). */
        REPORT,
        /** Create a new calendar collection (CalDAV). */
        MKCALENDAR,
        /** Search for resources matching a set of criteria (WebDAV). */
        SEARCH
    }

    data class Parameter(
        val name: String,
        val value: String,
        val urlEncode: Boolean = true,
        val contentType: String = "text/plain",
        val includeEquals: Boolean = true
    )

    data class File(
        val filePath: String,
        val parameterName: String,
        val mimeType: String,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpRequest

        if (enabled != other.enabled) return false
        if (name != other.name) return false
        if (comment != other.comment) return false
        if (!parameters.contentEquals(other.parameters)) return false
        if (bodyData != other.bodyData) return false
        if (!filesUpload.contentEquals(other.filesUpload)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = enabled.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + parameters.contentHashCode()
        result = 31 * result + bodyData.hashCode()
        result = 31 * result + filesUpload.contentHashCode()
        return result
    }
}

class HttpRequestBuilder : JMeterContainerBuilder<HttpRequest>() {
    override var name: String = "HTTP Request"
    var protocol: HttpRequest.Protocol? = null
    var serverNameOrIp: String? = null
    var portNumber: Int? = null
    var httpRequestMethod: HttpRequest.Method = HttpRequest.Method.GET
    var path: String? = null
    var contentEncoding: String? = null
    var redirectAutomatically: Boolean = false
    var followRedirects: Boolean = true
    var useKeepAlive: Boolean = true
    var useMultipartFormData: Boolean = false
    var browserCompatibleHeaders: Boolean = false

    var parameters: MutableList<HttpRequest.Parameter> = emptyList<HttpRequest.Parameter>().toMutableList()
    var bodyData: String = ""
    var filesUpload: MutableList<HttpRequest.File> = emptyList<HttpRequest.File>().toMutableList()

    // ToDo: implement Advanced input elements in JMeter GUI

    override fun doBuild(): HttpRequest {
        val hr = HttpRequest(
            name,
            comment,
            protocol,
            serverNameOrIp,
            portNumber,
            httpRequestMethod,
            path,
            contentEncoding,
            redirectAutomatically,
            followRedirects,
            useKeepAlive,
            useMultipartFormData,
            browserCompatibleHeaders,

            parameters.toTypedArray(),
            bodyData,
            filesUpload.toTypedArray(),

            enabled
        )
        return hr
    }

    override fun validate(element: HttpRequest) {
        require(element.parameters.isEmpty() || element.bodyData.isEmpty()) {
            "Cannot set both parameters and bodyData"
        }
    }
}