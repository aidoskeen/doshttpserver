package com.aidos.doshttpserver.server.messages.responses.fieldtype

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Serializable
data class CallLog(
    val beginning: String,
    val duration: String,
    val number: String,
    val name: String,
    val timesQueried: Int
) {
    fun toJsonObject(): JsonObject {
        return buildJsonObject {
            put("beginning", beginning)
            put("duration", duration)
            put("number", number)
            put("name", name)
            put("timesQueried", timesQueried)
        }
    }
}
