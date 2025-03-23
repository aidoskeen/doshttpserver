package com.aidos.doshttpserver.server.messages.responses

import com.aidos.doshttpserver.server.messages.responses.fieldtype.CallLog
import com.aidos.doshttpserver.toJsonArray
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Log(
    val calls: List<CallLog>
) : ServerResponse {
    override fun toJson(): String {
        val json = Json {
            prettyPrint = true
        }

        return json.encodeToString(calls.toJsonArray { call -> call.toJsonObject() })
    }
}