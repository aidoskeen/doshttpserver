package com.aidos.doshttpserver.data.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.aidos.doshttpserver.calls.CallLogManager
import com.aidos.doshttpserver.data.CallLogData
import com.aidos.doshttpserver.data.CallQueryInfo
import com.aidos.doshttpserver.data.currentcalldatastore.CurrentCallDataSource
import com.aidos.doshttpserver.data.currentcalldatastore.CurrentCallStatus
import com.aidos.doshttpserver.data.datasource.CallInfoDataSource
import com.aidos.doshttpserver.ui.main.viewstate.CallItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCallInfoRepository @Inject constructor(
    private val callInfoDataSource: CallInfoDataSource,
    private val callLogManager: CallLogManager,
    private val currentCallDataSource: CurrentCallDataSource,
): CallInfoRepository {
    // FIXME: Quick solution to store current call, but it should be stored in persistent store.
    private var currentCallStatus: CurrentCallStatus? = null
    override suspend fun getAllCallLogData(): List<CallLogData>? {
        return callLogManager.getAllCallLogs()?.map {
            val timesQueried = getCallInfoForNumber(it.phNumber).timesQueried
            insert(CallQueryInfo(it.phNumber, timesQueried + 1))
            CallLogData(
                callDate = it.callDate,
                phNumber = it.phNumber,
                callDuration = it.callDuration,
                name = it.name ?: it.phNumber,
                timesQueried = timesQueried
            )
        }
    }

    override suspend fun getCurrentCallStatus() = currentCallStatus

    override suspend fun setCurrentCallData(currentCallStatus: CurrentCallStatus) {
        this.currentCallStatus = currentCallStatus
    }

    override fun getCallItemsFlow(): Flow<List<CallItem>> = flow {
        val callItems = getAllCallLogData()?.map { CallItem(it.callDuration, it.name)}
        callItems?.let { emit(it) }
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