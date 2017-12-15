package com.antyzero.autoinposter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import com.antyzero.autoinposter.domain.InPostMessageDetector
import com.antyzero.autoinposter.domain.LinkExtractor
import com.antyzero.autoinposter.domain.data.Message
import com.antyzero.autoinposter.dsl.TAG
import com.antyzero.autoinposter.dsl.applicationComponent
import com.antyzero.autoinposter.logger.Logger
import com.antyzero.autoinposter.domain.network.InPostCalls
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SmsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var linkExtractor: LinkExtractor
    @Inject
    lateinit var inPostMessageDetector: InPostMessageDetector
    @Inject
    lateinit var inPostCalls: InPostCalls
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

                for (message in mergedMessages) {
                    if (inPostMessageDetector.isInPostMessage(message)) {
                        logger.i(TAG, "InPost message detected")
                        val linkId = linkExtractor.linkId(message.text)
                        if (linkId != null) {
                            logger.i(TAG, "Message contain valid link, ID found")
                            inPostCalls.keepOriginalDestination(linkId)
                                    .map {
                                        val body = it.body() ?: throw IllegalStateException("Cannot access response body")
                                        body.string()
                                    }
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        logger.i(TAG, "Request success")
                                        if (isResponseValid(it)) {
                                            logger.w(TAG, "Link might be not valid anymore")
                                            /*
                                            TODO this is risky situation, should be reported
                                            in general it might indicate that url format is different
                                            or more actions are required
                                             */
                                        } else {
                                            logger.i(TAG, "Total success, package will be delivered to original destination")
                                        }
                                    }, {
                                        logger.w(TAG, "Request to keep original destination failed")
                                        // TODO it might be good idea to send this again
                                    })
                        } else {
                            logger.i(TAG, "Message is not a valid one")
                        }
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
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

        private fun isResponseValid(message: String) =
                "niepoprawny link".toRegex().find(message.toLowerCase()) != null
    }
}