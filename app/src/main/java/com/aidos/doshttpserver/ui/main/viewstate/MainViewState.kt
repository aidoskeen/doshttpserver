package com.aidos.doshttpserver.ui.main.viewstate

data class MainViewState(
    val serverAddress: String = "",
    val isServerRunning: Boolean = false,
    val phoneCallList: List<CallItem> = listOf()
)