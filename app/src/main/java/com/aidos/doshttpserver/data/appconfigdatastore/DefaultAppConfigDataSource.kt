package com.aidos.doshttpserver.data.appconfigdatastore

import androidx.datastore.core.DataStore
import com.aidos.doshttpserver.proto.AppConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class DefaultAppConfigDataSource @Inject constructor(
    private val appConfigDataStore: DataStore<AppConfig>
): AppConfigDataSource {
    override val config = appConfigDataStore.data.map { AppConfiguration(it.firstLaunchTime, it.serverAddress) }
    override suspend fun setFirstLaunchTime(time: String) {
        appConfigDataStore.updateData {
            it.toBuilder().setFirstLaunchTime(time).build()
        }
    }

    override suspend fun getFirstLaunchTime(): String {
        return runBlocking { appConfigDataStore.data.first().firstLaunchTime }
    }

    override suspend fun setServerAddress(serverAddress: String) {
        appConfigDataStore.updateData {
            it.toBuilder().setServerAddress(serverAddress).build()
        }
    }
}
