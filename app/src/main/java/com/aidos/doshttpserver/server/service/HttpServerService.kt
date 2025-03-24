package com.aidos.doshttpserver.server.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.aidos.doshttpserver.server.HttpServer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HttpServerService(): Service() {
    @Inject
    lateinit var httpServer: HttpServer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(this.javaClass.simpleName, "Started the HTTP service")

        httpServer.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        httpServer.finish()
    }
}