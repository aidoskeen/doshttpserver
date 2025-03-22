package com.aidos.doshttpserver.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class HttpServer(
    val port: Int,
    val onRequestReceived: (HttpRequest) -> Unit,
) {
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    fun acceptNewConnection() {
        scope.launch {
            val serverSocket = ServerSocket(port)
            val clientSocket = serverSocket.accept()
            serverSocket.use {
                val incomingData = readIncomingData(clientSocket)
                if (incomingData.isNotEmpty()) {
                    val request = HttpRequest.parseFromString(incomingData.first())
                    request?.let { onRequestReceived(it) }
                }
            }
        }
    }

    fun readIncomingData(socket: Socket): List<String> {
        val inputStreamReader = InputStreamReader(socket.getInputStream())
        val bufferedReader = BufferedReader(inputStreamReader)
        return bufferedReader.readLines()
    }

    fun writeResponse(socket: Socket, response: String) {
        socket.getOutputStream().write(response.toByteArray(Charsets.UTF_8))
    }
}