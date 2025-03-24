package com.aidos.doshttpserver.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.Main.route) {
                    composable(Routes.Main.route) {
                        val viewState by viewModel.viewState.collectAsState()
                        DosHttpServerScreen(
                            serverAddress = viewState.serverAddress,
                            isServerRunning = viewState.isServerRunning,
                            onStartService = { startHttpServerService() },
                            onStopService = { stopHttpServerService() },
                            onNavigateToCallLogs = {
                                navController.navigate(Routes.Calls.route)
                            }
                        )
                    }

                    composable(Routes.Calls.route) {
                        val callItems by viewModel.callItemsState.collectAsState()
                        CallsScreen(callItems = callItems)
                    }
                }
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

sealed class Routes(val route: String) {
    data object Main : Routes("main")
    data object Calls : Routes("calls")
}
