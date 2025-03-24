package com.aidos.doshttpserver.data.datasource

import com.aidos.doshttpserver.data.CallQueryInfo
import com.aidos.doshttpserver.data.room.CallQueryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCallInfoDataSource @Inject constructor(
    private val callQueryDao: CallQueryDao
): CallInfoDataSource {
    override suspend fun insert(callQueryInfo: CallQueryInfo) = withContext(Dispatchers.IO) {
        callQueryDao.insert(callQueryInfo)
    }

    override suspend fun update(callQueryInfo: CallQueryInfo) = withContext(Dispatchers.IO) {
        callQueryDao.update(callQueryInfo)
    }

    override suspend fun getCallInfoForNumber(phoneNumber: String): CallQueryInfo = withContext(Dispatchers.IO) {
        callQueryDao.getCallInfoForNumber(phoneNumber)
    }
}