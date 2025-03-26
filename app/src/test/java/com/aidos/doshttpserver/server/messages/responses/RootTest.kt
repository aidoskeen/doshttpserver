package com.aidos.doshttpserver.server.messages.responses

import com.aidos.doshttpserver.server.messages.responses.fieldtype.ApiService
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class RootTest {

    @Test
    fun testRootToJsonConversion() {
        val rootJson = Root(
            start = "2018-05-02T23:00:00+00:00",
            services = listOf(
                ApiService(
                    name = "status",
                    uri = "http://192.168.1.100:12345/status"
                ),
                ApiService(
                    name = "log",
                    uri = "http://192.168.1.100:12345/log"
                )
            )
        ).toJson()

        val expectedRootJson = """
            {
                "start": "2018-05-02T23:00:00+00:00",
                "services": [
                    {
                        "name": "status",
                        "uri": "http://192.168.1.100:12345/status"
                    },
                    {
                        "name": "log",
                        "uri": "http://192.168.1.100:12345/log"
                    }
                ]
            }
        """.trimIndent()

        assertEquals(expectedRootJson, rootJson)
    }
}