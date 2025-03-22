package com.aidos.doshttpserver.server.messages.requests

sealed class ClientRequest {
    data object Status: ClientRequest()
    data object Log: ClientRequest()
}