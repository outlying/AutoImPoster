package com.antyzero.autoinposter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import com.antyzero.autoinposter.domain.InPostMessageDetector
import com.antyzero.autoinposter.domain.LinkExtractor
import com.antyzero.autoinposter.dsl.applicationComponent
import javax.inject.Inject


class SmsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var linkExtractor: LinkExtractor
    @Inject
    lateinit var inPostMessageDetector: InPostMessageDetector

    override fun onReceive(context: Context, intent: Intent) {

        context.applicationComponent.inject(this)

        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle.get(KEY_PDUS) as Array<*>

                for (i in pdusObj.indices) {

                    val smsMessage = createFromPdu(pdusObj[i] as ByteArray, bundle)
                    val phoneNumber = smsMessage.displayOriginatingAddress
                    val message = smsMessage.displayMessageBody
                    val inPostMessage = InPostMessageDetector.Message(phoneNumber, message)

                    // TODO what if message is going to be changed
                    if (inPostMessageDetector.isInPostMessage(inPostMessage)) {
                        val linkId = linkExtractor.linkId(inPostMessage.text)
                        if (linkId != null) {
                            // Web call
                        }
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