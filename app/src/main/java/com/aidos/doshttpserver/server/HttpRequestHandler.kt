package com.aidos.doshttpserver.server

import com.aidos.doshttpserver.server.messages.requests.RequestedService
import com.aidos.doshttpserver.server.messages.responses.Log
import com.aidos.doshttpserver.server.messages.responses.Root
import com.aidos.doshttpserver.server.messages.responses.ServerResponse
import com.aidos.doshttpserver.server.messages.responses.Status

class HttpRequestHandler(private val httpRequest: HttpRequest) {
    fun processRequest(): ServerResponse {

        return when (httpRequest.method) {
            HttpRequestMethod.GET -> handleGetRequest()
            HttpRequestMethod.PUT -> TODO()
            HttpRequestMethod.DELETE -> TODO()
            HttpRequestMethod.POST -> TODO()
            null -> TODO()
        }
    }

    private fun handleGetRequest(): ServerResponse {
        return when (httpRequest.requestedService) {
            RequestedService.Log -> prepareLogResponse()
            RequestedService.Root -> prepareRootResponse()
            RequestedService.Status -> prepareStatusResponse()
            null -> TODO()
        }
    }

    private fun prepareLogResponse(): Log {
        TODO()
    }

    private fun prepareRootResponse(): Root {
        TODO()
    }

    private fun prepareStatusResponse(): Status {
        TODO()
    }
}