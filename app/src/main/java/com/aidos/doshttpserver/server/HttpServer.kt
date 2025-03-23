package com.aidos.doshttpserver.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket

class HttpServer(
    val port: Int,
) {
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private var job: Job? = null

    fun start() {
        if (job != null) return
        job = scope.launch {
            val serverSocket = ServerSocket(port)
            acceptNewConnection(serverSocket)
        }
    }

    fun finish() {
        job?.run { if (isActive) cancel()  }
        job = null
    }

    suspend fun acceptNewConnection(serverSocket: ServerSocket) {
        while (job?.isActive == true) {
            val clientSocket = withContext(Dispatchers.IO) { serverSocket.accept() }
            if (clientSocket.isConnected) {
                coroutineScope {
                    serverSocket.use {
                        val incomingData = readIncomingData(clientSocket)
                        if (incomingData.isNotEmpty()) {
                            val request = HttpRequest.parseFromString(incomingData.first())
                            request?.let { nonNullRequest ->
                                val response =
                                    HttpRequestHandler(nonNullRequest).processRequest()
                                writeResponse(clientSocket, response.toJson())
                            }
                        }
                    }
                }
            }
        }
    }

    fun readIncomingData(socket: Socket): List<String> {
        val inputStreamReader = InputStreamReader(socket.getInputStream())
        val bufferedReader = BufferedReader(inputStreamReader)
        return bufferedReader.readLines()
    }

    suspend fun writeResponse(socket: Socket, response: String) = withContext(Dispatchers.IO) {
        socket.getOutputStream().write(response.toByteArray(Charsets.UTF_8))
    }
}