package com.aidos.doshttpserver.server.messages.responses

data class Status(
    val ongoing: String,
    val number: String,
    val name: String,
): ServerResponse {
    override fun toJson(): String {
        TODO("Not yet implemented")
    }
}