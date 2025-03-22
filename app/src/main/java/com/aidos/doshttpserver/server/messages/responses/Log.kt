package com.aidos.doshttpserver.server.messages.responses

import com.aidos.doshttpserver.server.messages.responses.fieldtype.CallLog

data class Log(
    val calls: List<CallLog>
) : ServerResponse {
    override fun toJson(): String {
        TODO("Not yet implemented")
    }
}