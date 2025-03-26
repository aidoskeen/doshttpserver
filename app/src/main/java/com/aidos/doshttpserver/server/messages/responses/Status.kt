package com.aidos.doshttpserver.server.messages.responses

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Serializable
data class Status(
    val ongoing: Boolean,
    val number: String,
    val name: String,
): ServerResponse {
    override fun toJson(): String {
        val json = Json { prettyPrint = true }

        return json.encodeToString(
            buildJsonObject {
                put("ongoing", ongoing)
                put("number", number)
                put("name", name)
            }
        )
    }
}