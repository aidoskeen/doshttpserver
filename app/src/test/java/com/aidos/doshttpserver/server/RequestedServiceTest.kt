package com.aidos.doshttpserver.server

import com.aidos.doshttpserver.server.messages.requests.RequestedService
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class RequestedServiceTest {
    @Test
    fun testRequestedServiceFromUri() {
        val statusStringUri = "http://localhost:1234/status"
        val rootStringUri = "http://localhost:1234"
        val logStringUri = "http://localhost:1234/log"
        val statusRequestedService = RequestedService.fromUri(statusStringUri)
        val rootRequestedService = RequestedService.fromUri(rootStringUri)
        val logRequestedService = RequestedService.fromUri(logStringUri)

        assertEquals(RequestedService.Status, statusRequestedService)
        assertEquals(RequestedService.Root, rootRequestedService)
        assertEquals(RequestedService.Log, logRequestedService)
    }
}