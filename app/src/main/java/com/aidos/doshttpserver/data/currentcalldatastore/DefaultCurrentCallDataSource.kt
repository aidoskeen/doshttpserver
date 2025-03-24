package com.aidos.doshttpserver.data.currentcalldatastore

import androidx.datastore.core.DataStore
import com.aidos.doshttpserver.proto.CurrentCall
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultCurrentCallDataSource @Inject constructor(
    val currentCallDataStore: DataStore<CurrentCall>
): CurrentCallDataSource {
    override val currentCall = currentCallDataStore.data.map { CurrentCallStatus(it.ongoing, it.number, it.name) }

    override suspend fun setCurrentCall(currentCallStatus: CurrentCallStatus) {
        currentCallDataStore.updateData { callData ->
            callData.toBuilder()
                .setNumber(currentCallStatus.number)
                .setName(currentCallStatus.name)
                .setOngoing(currentCallStatus.ongoing)
                .build()
        }
    }
}