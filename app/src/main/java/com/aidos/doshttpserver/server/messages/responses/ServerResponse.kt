package com.aidos.doshttpserver.server.messages.responses

sealed interface ServerResponse {
    fun toJson(): String
}