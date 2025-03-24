package com.aidos.doshttpserver.data.datasource

import com.aidos.doshttpserver.data.CallQueryInfo
import com.aidos.doshttpserver.data.room.CallQueryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CallInfoDataSource {
    suspend fun insert(callQueryInfo: CallQueryInfo)

    suspend fun update(callQueryInfo: CallQueryInfo)

    suspend fun getCallInfoForNumber(phoneNumber: String): CallQueryInfo
}