package com.antyzero.autoinposter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import com.antyzero.autoinposter.dsl.showToast


class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.showToast("Test")

        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle.get("pdus") as Array<*>

                for (i in pdusObj.indices) {
                    val smsMessage = SmsMessage.createFromPdu(pdusObj[i] as ByteArray)
                    val phoneNumber = smsMessage.displayOriginatingAddress
                    val message = smsMessage.displayMessageBody

                    InPostMessageDetector.isInPostMessage(
                            InPostMessageDetector.Message(phoneNumber, message))
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}