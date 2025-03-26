package com.aidos.doshttpserver.data.appconfigdatastore

import kotlinx.coroutines.flow.Flow

interface AppConfigDataSource {
    val config: Flow<AppConfiguration>

    suspend fun setFirstLaunchTime(time: String)
    suspend fun getFirstLaunchTime(): String

    suspend fun setServerAddress(serverAddress: String)
}
