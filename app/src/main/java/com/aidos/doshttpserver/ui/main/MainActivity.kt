package com.aidos.doshttpserver.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.aidos.doshttpserver.server.service.HttpServerService
import com.aidos.doshttpserver.ui.theme.DosHttpServerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DosHttpServerTheme {
                val viewModel = hiltViewModel<MainViewModel>()
                val viewState by viewModel.viewState.collectAsState()

                DosHttpServerScreen(
                    serverAddress = viewState.serverAddress,
                    isServerRunning = viewState.isServerRunning,
                    onStartService = { startHttpServerService() },
                    onStopService = { stopHttpServerService() },
                    onNavigateToCallLogs = {}
                )
            }
        }
    }

    private fun stopHttpServerService() {
        stopService(Intent(this, HttpServerService::class.java))
    }

    private fun startHttpServerService() {
        startService(Intent(this, HttpServerService::class.java))
    }
}
