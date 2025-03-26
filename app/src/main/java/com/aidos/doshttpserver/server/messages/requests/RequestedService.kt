package com.aidos.doshttpserver.server.messages.requests

import java.net.URI

sealed class RequestedService {
    data object Root: RequestedService()
    data object Status: RequestedService()
    data object Log: RequestedService()

    companion object {
        private const val STATUS_PATH = "/status"
        private const val EMPTY = "/"
        private const val LOG_PATH = "/log"

        fun fromUri(uriString: String): RequestedService? {
            val uri = URI(uriString)

            return when (uri.path) {
                EMPTY -> Root
                STATUS_PATH -> Status
                LOG_PATH -> Log
                else -> null
            }
        }
    }
}