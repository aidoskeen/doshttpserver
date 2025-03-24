package com.aidos.doshttpserver.data.currentcalldatastore

import kotlinx.coroutines.flow.Flow

interface CurrentCallDataSource {
    val currentCall: Flow<CurrentCallStatus>

    suspend fun setCurrentCall(currentCallStatus: CurrentCallStatus)
}
