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
import com.antyzero.autoinposter.network.InPostCalls
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

                            inPostCalls.keepOriginalDestination(linkId)
                                    .map {
                                        val body = it.body() ?: throw IllegalStateException("Cannot access response body")
                                        body.string()
                                    }
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        // TODO check if response is correct
                                    }, {
                                        it.toString()
                                        println(it)
                                        // TODO log error
                                    })
                        }
                        // But what if there is no link
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