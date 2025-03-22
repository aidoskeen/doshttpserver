package com.aidos.doshttpserver.server

class HttpRequestHandler(private val httpRequest: HttpRequest) {
    fun processRequest(): String {
        when (httpRequest.method) {
            HttpRequestMethod.GET -> TODO()
            HttpRequestMethod.PUT -> TODO()
            HttpRequestMethod.DELETE -> TODO()
            HttpRequestMethod.POST -> TODO()
            null -> TODO()
        }

        return ""
    }
}