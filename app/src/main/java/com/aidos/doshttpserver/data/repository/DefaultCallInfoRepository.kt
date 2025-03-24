package com.aidos.doshttpserver.data.repository

import com.aidos.doshttpserver.calls.CallLogManager
import com.aidos.doshttpserver.data.CallLogData
import com.aidos.doshttpserver.data.CallQueryInfo
import com.aidos.doshttpserver.data.datasource.CallInfoDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCallInfoRepository @Inject constructor(
    private val callInfoDataSource: CallInfoDataSource,
    private val callLogManager: CallLogManager
): CallInfoRepository {
    override suspend fun getAllCallLogData(): List<CallLogData>? {
        return callLogManager.getAllCallLogs()?.map {
            val timesQueried = getCallInfoForNumber(it.phNumber).timesQueried
            insert(CallQueryInfo(it.phNumber, timesQueried + 1))
            CallLogData(
                callDate = it.callDate,
                phNumber = it.phNumber,
                callDuration = it.callDuration,
                name = it.name,
                timesQueried = timesQueried
            )
        }
    }

    override suspend fun insert(callQueryInfo: CallQueryInfo) = withContext(Dispatchers.IO) {
        callInfoDataSource.insert(callQueryInfo)
    }

    override suspend fun update(callQueryInfo: CallQueryInfo) = withContext(Dispatchers.IO) {
        callInfoDataSource.update(callQueryInfo)
    }

    override suspend fun getCallInfoForNumber(phoneNumber: String): CallQueryInfo = withContext(Dispatchers.IO) {
        callInfoDataSource.getCallInfoForNumber(phoneNumber)
    }
}