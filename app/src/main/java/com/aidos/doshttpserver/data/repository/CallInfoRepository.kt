package com.aidos.doshttpserver.data.repository

import com.aidos.doshttpserver.data.CallLogData
import com.aidos.doshttpserver.data.CallQueryInfo
import com.aidos.doshttpserver.data.currentcalldatastore.CurrentCallStatus
import com.aidos.doshttpserver.ui.main.viewstate.CallItem
import kotlinx.coroutines.flow.Flow

interface CallInfoRepository {
    suspend fun insert(callQueryInfo: CallQueryInfo)

    suspend fun update(callQueryInfo: CallQueryInfo)

    suspend fun getCallInfoForNumber(phoneNumber: String): CallQueryInfo

    suspend fun getAllCallLogData(): List<CallLogData>?

    suspend fun getCurrentCallFlow(): Flow<CurrentCallStatus>

    suspend fun setCurrentCallData(currentCallStatus: CurrentCallStatus)

    fun getCallItemsFlow(): Flow<List<CallItem>>
}