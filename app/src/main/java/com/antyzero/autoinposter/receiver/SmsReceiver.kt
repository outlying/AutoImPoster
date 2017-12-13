package com.antyzero.autoinposter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import com.antyzero.autoinposter.domain.InPostMessageDetector
import com.antyzero.autoinposter.dsl.showToast


class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle.get(KEY_PDUS) as Array<*>

                for (i in pdusObj.indices) {

                    val smsMessage = createFromPdu(pdusObj[i] as ByteArray, bundle)
                    val phoneNumber = smsMessage.displayOriginatingAddress
                    val message = smsMessage.displayMessageBody
                    val inPostMessage = InPostMessageDetector.Message(phoneNumber, message)

                    if (InPostMessageDetector().isInPostMessage(inPostMessage)) {
                        context.showToast("Got InPost message")
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Creates proper SmsMessage from byte input
     */
    private fun createFromPdu(bytes: ByteArray, bundle: Bundle) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val format = bundle.getString("format")
                SmsMessage.createFromPdu(bytes, format)
            } else {
                @Suppress("DEPRECATION")
                SmsMessage.createFromPdu(bytes)
            }

    companion object {

        private const val KEY_PDUS = "pdus"
    }
}