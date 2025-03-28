package com.aidos.doshttpserver.data.repository

import com.aidos.doshttpserver.data.appconfigdatastore.AppConfigDataSource
import com.aidos.doshttpserver.data.appconfigdatastore.AppConfiguration
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultAppConfigRepository @Inject constructor(
    private val appConfigDataSource: AppConfigDataSource
): AppConfigRepository {
    override suspend fun getConfigFlow(): Flow<AppConfiguration> = appConfigDataSource.config

    override suspend fun setFirstLaunchTime(time: String) = appConfigDataSource.setFirstLaunchTime(time)

    override suspend fun getFirstLaunchTime() = appConfigDataSource.getFirstLaunchTime()

    override suspend fun setServerAddress(serverAddress: String) = appConfigDataSource.setServerAddress(serverAddress)
}