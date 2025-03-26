package com.aidos.doshttpserver.server.messages.responses

import com.aidos.doshttpserver.server.messages.responses.fieldtype.ApiService
import com.aidos.doshttpserver.toJsonArray
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Serializable
data class Root(
    val start: String,
    val services: List<ApiService>
): ServerResponse {
    override fun toJson(): String {
        val json = Json { prettyPrint = true }
        return json.encodeToString(
            buildJsonObject {
                put("start", start)
                put("services", services.toJsonArray { it.toJsonObject() })
            }
        )
    }

}
