package com.aidos.doshttpserver.data.repository

import com.aidos.doshttpserver.calls.CallData
import com.aidos.doshttpserver.calls.CallLogManager
import com.aidos.doshttpserver.data.CallLogDataWithTimesQueried
import com.aidos.doshttpserver.data.CallWithTimesQueried
import com.aidos.doshttpserver.data.appconfigdatastore.AppConfigDataSource
import com.aidos.doshttpserver.data.appconfigdatastore.CurrentCallStatus
import com.aidos.doshttpserver.data.datasource.CallInfoDataSource
import com.aidos.doshttpserver.ui.main.viewstate.CallItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultCallInfoRepository @Inject constructor(
    private val callInfoDataSource: CallInfoDataSource,
    private val callLogManager: CallLogManager,
    private val appConfigDataSource: AppConfigDataSource,
): CallInfoRepository {
    // FIXME: Quick solution to store current call, but it should be stored in persistent store.
    private var currentCallStatus: CurrentCallStatus? = null
    override suspend fun getCallLogsWithTimesQueried(): List<CallLogDataWithTimesQueried>? {
        val allCallLogs = callLogManager.getAllCallLogsFromFirstLaunch(appConfigDataSource.getFirstLaunchTime())
        allCallLogs?.let { updateTimesQueried(it) }

        return allCallLogs?.map {
            val timesQueried = getCallInfoForNumber(it.phNumber)?.timesQueried ?: 0
            CallLogDataWithTimesQueried(
                callDate = it.callDate,
                phNumber = it.phNumber,
                callDuration = it.callDuration,
                name = it.name ?: it.phNumber,
                timesQueried = timesQueried
            )
        }
    }

    private suspend fun updateTimesQueried(allCallLogs: List<CallData>) {
        allCallLogs.distinctBy { it.phNumber }.forEach {
            getCallInfoForNumber(it.phNumber).run {
                if (this == null) {
                    insert(CallWithTimesQueried(it.phNumber, 0))
                } else {
                    update(this.copy(timesQueried = timesQueried + 1))
                }
            }
        }
    }

    override fun getCurrentCallStatus() = currentCallStatus

    override fun setCurrentCallData(currentCallStatus: CurrentCallStatus) {
        this.currentCallStatus = currentCallStatus
    }

    override fun getCallItemsFlow(): Flow<List<CallItem>> = flow {
        val callItems = callLogManager.getAllCallLogs()?.map { CallItem(it.callDuration, it.name ?: it.phNumber)}
        callItems?.let { emit(it) }
    }

    override suspend fun insert(callWithTimesQueried: CallWithTimesQueried) = callInfoDataSource.insert(callWithTimesQueried)


    override suspend fun update(callWithTimesQueried: CallWithTimesQueried) = callInfoDataSource.update(callWithTimesQueried)


    override suspend fun getCallInfoForNumber(phoneNumber: String): CallWithTimesQueried? = callInfoDataSource.getCallInfoForNumber(phoneNumber)

}