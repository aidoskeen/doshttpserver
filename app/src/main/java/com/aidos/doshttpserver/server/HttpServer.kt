package com.aidos.doshttpserver.server

import android.util.Log
import com.aidos.doshttpserver.data.repository.AppConfigRepository
import com.aidos.doshttpserver.data.repository.CallInfoRepository
import com.aidos.doshttpserver.server.messages.requests.RequestedService
import com.aidos.doshttpserver.server.messages.responses.LogRequest
import com.aidos.doshttpserver.server.messages.responses.Root
import com.aidos.doshttpserver.server.messages.responses.ServerResponse
import com.aidos.doshttpserver.server.messages.responses.Status
import com.aidos.doshttpserver.server.messages.responses.fieldtype.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import javax.inject.Inject

class HttpServer @Inject constructor(
    val callInfoRepository: CallInfoRepository,
    val appConfigRepository: AppConfigRepository
) {
    private val port = 8888

    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private var job: Job? = null
    var serverSocket: ServerSocket? = null
    fun start() {
        if (job != null) return
        if (serverSocket != null) return
        job = scope.launch {
            runCatching {
                serverSocket = ServerSocket(port)
                appConfigRepository.setServerAddress("127.0.0.1:$port")
                acceptNewConnectionAndProcess(serverSocket!!)
            }.onFailure {
                serverSocket?.close()
                it.printStackTrace()
            }
        }
    }

    fun finish() {
        runCatching { serverSocket?.close() }
        job?.run { if (isActive) cancel()  }
        job = null
        serverSocket = null
    }

    suspend fun acceptNewConnectionAndProcess(serverSocket: ServerSocket) = withContext(Dispatchers.IO) {
        while (job?.isActive == true) {
            serverSocket.accept().use { socket ->
                Log.d("HttpServer", "New connection ${socket.inetAddress}")
                val incomingData = readIncomingData(socket)
                val request = HttpRequest.parseFromString(incomingData)
                request?.let { nonNullRequest ->
                    val response = processRequest(nonNullRequest)
                    val httpResponse = "HTTP/1.1 200 OK\r\n\r\n${response?.toJson()}"
                    socket.getOutputStream().write(httpResponse.toByteArray(charset("UTF-8")))
                }
            }
        }
    }

    suspend fun readIncomingData(socket: Socket): String = withContext(Dispatchers.IO) {
        val inputStreamReader = InputStreamReader(socket.getInputStream())
        val bufferedReader = BufferedReader(inputStreamReader)
        return@withContext bufferedReader.readLine()
    }

    suspend fun processRequest(httpRequest: HttpRequest): ServerResponse? {
        return when (httpRequest.method) {
            HttpRequestMethod.GET -> handleGetRequest(httpRequest)
            HttpRequestMethod.PUT -> TODO()
            HttpRequestMethod.DELETE -> TODO()
            HttpRequestMethod.POST -> TODO()
            null -> TODO()
        }
    }

    private suspend fun handleGetRequest(httpRequest: HttpRequest): ServerResponse? {
        return when (httpRequest.requestedService) {
            RequestedService.Log -> prepareLogResponse()
            RequestedService.Root -> prepareRootResponse()
            RequestedService.Status -> prepareStatusResponse()
            null -> null
        }
    }

    private suspend fun prepareLogResponse(): LogRequest? {
        val calls = callInfoRepository.getCallLogsWithTimesQueried()?.map {
            it.toResponseCallLog()
        }

        return calls?.let {
            LogRequest(calls = it)
        }
    }

    private fun prepareRootResponse(): Root {
        return Root(
            start = System.currentTimeMillis().toString(),
            services = listOf(
                ApiService("Log", "/log"),
                ApiService("Status", "/status")
            )
        )
    }

    private fun prepareStatusResponse(): Status? {
        val currentCall = callInfoRepository.getCurrentCallStatus()
        return currentCall?.toStatus()
    }
}