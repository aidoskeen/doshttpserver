package com.aidos.doshttpserver.data

data class CallLogData(
    val callDate: String,
    val phNumber: String,
    val callDuration: String,
    val name: String,
    val timesQueried: Int
)
