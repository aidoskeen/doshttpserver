package com.aidos.doshttpserver.server.messages.responses

import com.aidos.doshttpserver.server.messages.responses.fieldtype.CallLog
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class LogTest {
    @Test
    fun testLogToJsonConversion() {
        val calls = listOf(
            CallLog(
                beginning = "2018-05-02T23:00:00+00:00",
                duration = "498",
                number = "+120255550203",
                name = "JaneDoe",
                timesQueried = "5"
            ),
            CallLog(
                beginning = "2018-05-01T09:00:00+00:00",
                duration = "721",
                number = "+120255550108",
                name = "JohnDoe",
                timesQueried = "0"
            )
        )


        val serializedLogJson = Log(calls).toJson()
        val expectedLogJson = """
            [
                {
                    "beginning": "2018-05-02T23:00:00+00:00",
                    "duration": "498",
                    "number": "+120255550203",
                    "name": "JaneDoe",
                    "timesQueried": "5"
                },
                {
                    "beginning": "2018-05-01T09:00:00+00:00",
                    "duration": "721",
                    "number": "+120255550108",
                    "name": "JohnDoe",
                    "timesQueried": "0"
                }
            ]
        """.trimIndent()
        assertEquals(expectedLogJson, serializedLogJson)
    }
}

