package com.aidos.doshttpserver.calls

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.aidos.doshttpserver.data.currentcalldatastore.CurrentCallStatus
import com.aidos.doshttpserver.data.repository.CallInfoRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class CallStateReceiver: BroadcastReceiver() {

    @Inject
    lateinit var callInfoRepository: CallInfoRepository
    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    override fun onReceive(context: Context?, intent: Intent?) {
        val telephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
        if (telephonyManager == null) {
            Log.e(this.javaClass.simpleName, "TelephonyManager is null")
            return
        }

        telephonyManager.listen(object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String) {
                super.onCallStateChanged(state, incomingNumber)
                Log.d("CallStateReceiver","call from number : $incomingNumber")
                coroutineScope.launch {
                    when (state) {
                        TelephonyManager.CALL_STATE_IDLE -> {
                            callInfoRepository.setCurrentCallData(
                                currentCallStatus = CurrentCallStatus(
                                    ongoing = false,
                                    number = "",
                                    name = ""
                                )
                            )
                        }

                        TelephonyManager.CALL_STATE_OFFHOOK, TelephonyManager.CALL_STATE_RINGING -> {
                            callInfoRepository.setCurrentCallData(
                                currentCallStatus = CurrentCallStatus(
                                    ongoing = true,
                                    number = incomingNumber,
                                    name = getContactName(incomingNumber, context)
                                )
                            )
                        }

                        else -> {}
                    }
                }

            }
        }, PhoneStateListener.LISTEN_CALL_STATE)
    }

    private suspend fun getContactName(number: String, context: Context): String = withContext(Dispatchers.IO) {
        val projection = arrayOf(
            ContactsContract.PhoneLookup.DISPLAY_NAME,
            ContactsContract.PhoneLookup.NUMBER,
            ContactsContract.PhoneLookup.HAS_PHONE_NUMBER
        )

        val contactUri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(number)
        )

        val cursor = context.contentResolver.query(
            contactUri,
            projection, null, null, null
        )

        if (cursor == null) return@withContext ""

        val contactName = if (cursor.moveToFirst())
            cursor.getString(
                cursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME)
            )
        else
            null

        cursor.close()
        return@withContext contactName ?: ""
    }
}