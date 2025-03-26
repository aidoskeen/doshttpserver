package com.aidos.doshttpserver.server.messages.responses

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class StatusTest {
    @Test
    fun testStatusToJsonConverting() {
        val status = Status(ongoing = true, number = "+12025550108", name = "John Doe" )
        val serializedStatusJson = status.toJson()
        val expectedStatusJson = """
            {
                "ongoing": truex,
                "number": "+12025550108",
                "name": "John Doe"
            }
        """.trimIndent()

        assertEquals(expectedStatusJson, serializedStatusJson)
    }
}