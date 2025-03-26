package com.aidos.doshttpserver.data.datasource

import com.aidos.doshttpserver.data.CallWithTimesQueried
import com.aidos.doshttpserver.data.room.CallQueryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultCallInfoDataSource @Inject constructor(
    private val callQueryDao: CallQueryDao
): CallInfoDataSource {
    override suspend fun insert(callWithTimesQueried: CallWithTimesQueried) = withContext(Dispatchers.IO) {
        callQueryDao.insert(callWithTimesQueried)
    }

    override suspend fun update(callWithTimesQueried: CallWithTimesQueried) = withContext(Dispatchers.IO) {
        callQueryDao.update(callWithTimesQueried)
    }

    override suspend fun getCallInfoForNumber(phoneNumber: String): CallWithTimesQueried? = withContext(Dispatchers.IO) {
        callQueryDao.getCallInfoForNumber(phoneNumber)
    }
}