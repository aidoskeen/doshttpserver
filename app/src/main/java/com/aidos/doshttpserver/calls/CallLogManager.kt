package com.aidos.doshttpserver.calls

import android.content.Context
import android.provider.CallLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CallLogManager(private val appContext: Context) {
    suspend fun getAllCallLogs(): List<CallData>? = withContext(Dispatchers.IO) {
        val callUri = CallLog.Calls.CONTENT_URI
        val cursor = appContext.contentResolver.query(callUri, null, null, null, null) ?: return@withContext null

        return@withContext buildList {
            while (cursor.moveToNext()) {
                val callDate = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))
                val phNumber = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
                val callDuration = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME))
                add(CallData(callDate, phNumber, callDuration, name))
            }

            cursor.close()
        }
    }
}

data class CallData(
    val callDate: String,
    val phNumber: String,
    val callDuration: String,
    val name: String,
)