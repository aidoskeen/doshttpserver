package com.aidos.doshttpserver.calls

import android.content.Context
import android.provider.CallLog
import android.util.Log
import androidx.core.database.getStringOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject


class CallLogManager @Inject constructor(
    private val appContext: Context
) {
    suspend fun getAllCallLogsFromFirstLaunch(firstLaunchTime: String): List<CallData>? = withContext(Dispatchers.IO) {
        CallLog.Calls.DATE + ">= ?"
        val callUri = CallLog.Calls.CONTENT_URI
        val cursor = appContext.contentResolver.query(callUri, null,  CallLog.Calls.DATE + ">= ?", arrayOf(firstLaunchTime), null)
        if (cursor == null) {
            Log.e("CallLogManager", "Cursor is null.")
            return@withContext null
        }

        return@withContext buildList {
            while (cursor.moveToNext()) {
                kotlin.runCatching {
                    val callDate = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))
                        ?.toLongOrNull()
                        ?.millisToLocalDateString()

                    val phNumber = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
                    val callDuration = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION))
                    val name = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME))
                    Log.i("Calls", "callDate:$callDate phPhone:$phNumber callDuration:$callDuration Name:$name")
                    if (callDate != null && phNumber != null && callDuration != null)
                        add(CallData(callDate, phNumber, callDuration, name))
                }
            }

            cursor.close()
        }
    }

    suspend fun getAllCallLogs(): List<CallData>? = withContext(Dispatchers.IO) {
        val callUri = CallLog.Calls.CONTENT_URI
        val cursor = appContext.contentResolver.query(callUri, null, null, null, null)
        if (cursor == null) {
            Log.e("CallLogManager", "Cursor is null.")
            return@withContext null
        }

        return@withContext buildList {
            while (cursor.moveToNext()) {
                kotlin.runCatching {
                    val callDate = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))
                        ?.toLongOrNull()
                        ?.millisToLocalDateString()

                    val phNumber = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
                    val callDuration = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION))
                    val name = cursor.getStringOrNull(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME))
                    Log.i("Calls", "callDate:$callDate phPhone:$phNumber callDuration:$callDuration Name:$name")
                    if (callDate != null && phNumber != null && callDuration != null)
                        add(CallData(callDate, phNumber, callDuration, name))
                }
            }

            cursor.close()
        }
    }
}

private fun Long.millisToLocalDateString(): String? {
    return LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this),
            ZoneId.systemDefault()
        )?.toString()
}

data class CallData(
    val callDate: String,
    val phNumber: String,
    val callDuration: String,
    val name: String?,
)