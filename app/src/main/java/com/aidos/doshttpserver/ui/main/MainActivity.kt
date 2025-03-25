package com.aidos.doshttpserver.ui.main

import android.Manifest
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DosHttpServerTheme {
                val viewModel = hiltViewModel<MainViewModel>()
                val navController = rememberNavController()
                val permissionState = rememberPermissionState(permission = Manifest.permission.READ_CALL_LOG) { }

                NavHost(navController = navController, startDestination = Routes.Main.route) {
                    composable(Routes.Main.route) {
                        val viewState by viewModel.viewState.collectAsState()
                        DosHttpServerScreen(
                            serverAddress = viewState.serverAddress,
                            isServerRunning = viewState.isServerRunning,
                            onStartService = {
                                viewModel.setIsServerRunning(true)
                                startService(Intent(this@MainActivity, HttpServerService::class.java))
                            },
                            onStopService = {
                                viewModel.setIsServerRunning(false)
                                stopService(Intent(this@MainActivity, HttpServerService::class.java))
                                            },
                            onNavigateToCallLogs = {
                                if (permissionState.status.isGranted) {
                                    navController.navigate(Routes.Calls.route)
                                } else {
                                    permissionState.launchPermissionRequest()
                                }
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
}

sealed class Routes(val route: String) {
    data object Main : Routes("main")
    data object Calls : Routes("calls")
}
