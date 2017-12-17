package com.antyzero.autoinposter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import com.antyzero.autoinposter.domain.ImPoster
import com.antyzero.autoinposter.domain.TAG
import com.antyzero.autoinposter.domain.data.Message
import com.antyzero.autoinposter.domain.logger.Logger
import com.antyzero.autoinposter.dsl.applicationComponent
import javax.inject.Inject


class SmsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var imPoster: ImPoster
    @Inject
    lateinit var logger: Logger

    override fun onReceive(context: Context, intent: Intent) {
        context.applicationComponent.inject(this)

        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle.get(KEY_PDUS) as Array<*>

                val messages = mutableListOf<Message>()

                for (i in pdusObj.indices) {
                    val smsMessage = createFromPdu(pdusObj[i] as ByteArray, bundle)
                    val phoneNumber = smsMessage.displayOriginatingAddress
                    val message = smsMessage.displayMessageBody
                    messages.add(Message(phoneNumber, message))
                }

                val mergedMessages = messages
                        .groupBy { it.phoneNumber }
                        .map {
                            val phone = it.key
                            val message = it.value.joinToString(separator = "") { it.text }
                            Message(phone, message)
                        }

                imPoster.verifyMessages(mergedMessages)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            logger.e(TAG, "Fatal error during message processing", e)
        }
    }

    companion object {

        private const val KEY_PDUS = "pdus"

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
    }
}