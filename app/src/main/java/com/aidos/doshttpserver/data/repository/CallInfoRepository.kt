package com.aidos.doshttpserver.data.repository

import com.aidos.doshttpserver.data.CallLogData
import com.aidos.doshttpserver.data.CallQueryInfo

interface CallInfoRepository {
    suspend fun insert(callQueryInfo: CallQueryInfo)

    suspend fun update(callQueryInfo: CallQueryInfo)

    suspend fun getCallInfoForNumber(phoneNumber: String): CallQueryInfo

    suspend fun getAllCallLogData(): List<CallLogData>?
}