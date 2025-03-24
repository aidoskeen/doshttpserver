package com.aidos.doshttpserver.data.currentcalldatastore

data class CurrentCallStatus(
    val ongoing: Boolean,
    val number: String,
    val name: String
)
