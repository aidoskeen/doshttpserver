package com.aidos.doshttpserver.server.messages.responses.fieldtype

import java.io.Serializable

data class CallLog(
    val beginning: String,
    val duration: String,
    val number: String,
    val name: String,
    val timesQueried: String
): Serializable
