package com.aidos.doshttpserver.data.repository

import com.aidos.doshttpserver.data.appconfigdatastore.AppConfiguration
import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    suspend fun getConfigFlow(): Flow<AppConfiguration>

    suspend fun setFirstLaunchTime(time: String)
    suspend fun getFirstLaunchTime(): String

    suspend fun setServerAddress(serverAddress: String)
}