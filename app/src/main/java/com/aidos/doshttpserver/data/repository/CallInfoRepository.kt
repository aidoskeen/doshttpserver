package com.aidos.doshttpserver.data.repository

import com.aidos.doshttpserver.data.CallLogData
import com.aidos.doshttpserver.data.CallWithTimesQueried
import com.aidos.doshttpserver.data.currentcalldatastore.CurrentCallStatus
import com.aidos.doshttpserver.ui.main.viewstate.CallItem
import kotlinx.coroutines.flow.Flow

interface CallInfoRepository {
    suspend fun insert(callWithTimesQueried: CallWithTimesQueried)

    suspend fun update(callWithTimesQueried: CallWithTimesQueried)

    suspend fun getCallInfoForNumber(phoneNumber: String): CallWithTimesQueried?

    suspend fun getAllCallLogData(): List<CallLogData>?

    fun getCurrentCallStatus(): CurrentCallStatus?

    fun setCurrentCallData(currentCallStatus: CurrentCallStatus)

    fun getCallItemsFlow(): Flow<List<CallItem>>
}