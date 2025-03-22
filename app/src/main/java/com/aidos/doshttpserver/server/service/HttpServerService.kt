package com.aidos.doshttpserver.server.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.aidos.doshttpserver.server.HttpServer

class HttpServerService(): Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(this.javaClass.simpleName, "Started the HTTP service")
        val httpServer = HttpServer(HTTP_SERVER_PORT)

        httpServer.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val HTTP_SERVER_PORT = 8888
    }
}