package com.aidos.doshttpserver.data.datasource

import com.aidos.doshttpserver.data.CallWithTimesQueried

interface CallInfoDataSource {
    suspend fun insert(callWithTimesQueried: CallWithTimesQueried)

    suspend fun update(callWithTimesQueried: CallWithTimesQueried)

    suspend fun getCallInfoForNumber(phoneNumber: String): CallWithTimesQueried?
}