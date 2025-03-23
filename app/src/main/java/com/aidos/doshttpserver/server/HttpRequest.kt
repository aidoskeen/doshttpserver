package com.aidos.doshttpserver.server

import android.util.Log
import com.aidos.doshttpserver.server.messages.requests.RequestedService

data class HttpRequest(
    val method: HttpRequestMethod?,
    val requestedService: RequestedService?,
) {
    companion object {
        private const val HEADER_DELIMETER = " "
        fun parseFromString(headerString: String): HttpRequest? {
            val list = headerString.split(HEADER_DELIMETER)
            return if (list.size > 1) {
                HttpRequest(
                    method = HttpRequestMethod.fromString(list[0]),
                    requestedService = RequestedService.fromUri(list[1])
                )
            } else {
                Log.e(Companion::class.java.simpleName, "Could not parse Http header")
                null
            }
        }
    }
}