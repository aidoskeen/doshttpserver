package com.aidos.doshttpserver.ui.main

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aidos.doshttpserver.calls.CallStateReceiver
import com.aidos.doshttpserver.server.service.HttpServerService
import com.aidos.doshttpserver.ui.theme.DosHttpServerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val broadcastReceiver = CallStateReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DosHttpServerTheme {
                val viewModel = hiltViewModel<MainViewModel>()
                val navController = rememberNavController()

                requestPermissions()

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

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun requestPermissions() {
        var isPhoneStatePermissionGiven by remember { mutableStateOf(false) }
        var isReadContactsPermissionGiven by remember { mutableStateOf(false) }
        var isReadLogPermissionGiven by remember { mutableStateOf(false) }
        val phoneStatePermission = rememberPermissionState(permission = Manifest.permission.READ_PHONE_STATE) {
            isPhoneStatePermissionGiven = it
        }
        val readContactsPermission = rememberPermissionState(permission = Manifest.permission.READ_CONTACTS) {
            isReadContactsPermissionGiven = it
        }
        val readLogPermission = rememberPermissionState(permission = Manifest.permission.READ_CALL_LOG) {
            isReadLogPermissionGiven = true
        }

        LaunchedEffect (isReadLogPermissionGiven, isReadContactsPermissionGiven, isPhoneStatePermissionGiven) {
            when {
                !isReadLogPermissionGiven -> readLogPermission.launchPermissionRequest()
                !isReadContactsPermissionGiven -> readContactsPermission.launchPermissionRequest()
                !isPhoneStatePermissionGiven -> phoneStatePermission.launchPermissionRequest()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter().apply {
            addAction("android.intent.action.PHONE_STATE")
        }

        registerReceiver(broadcastReceiver, filter);
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}

sealed class Routes(val route: String) {
    data object Main : Routes("main")
    data object Calls : Routes("calls")
}
