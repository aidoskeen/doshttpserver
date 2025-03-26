package com.aidos.doshttpserver.server.messages.responses.fieldtype

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Serializable
data class ApiService(
    val name: String,
    val uri: String
) {
    fun toJsonObject(): JsonObject {
        return buildJsonObject {
            put("name", name)
            put("uri", uri)
        }
    }
}
