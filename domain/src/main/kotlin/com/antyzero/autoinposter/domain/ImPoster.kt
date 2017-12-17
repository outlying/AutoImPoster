package com.antyzero.autoinposter.domain

import com.antyzero.autoinposter.domain.data.Message
import com.antyzero.autoinposter.domain.issue.IssueReporter
import com.antyzero.autoinposter.domain.logger.Logger
import com.antyzero.autoinposter.domain.network.InPostCalls
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class ImPoster(
        private val inPostMessageDetector: InPostMessageDetector,
        private val inPostCalls: InPostCalls,
        private val receivingSchedulers: Scheduler,
        private val linkExtractor: LinkExtractor = LinkExtractor,
        private val logger: Logger = Logger,
        private val issueReporter: IssueReporter) {

    fun verifyMessages(messages: Collection<Message>) {

        for (message in messages) {
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
                            .observeOn(receivingSchedulers)
                            .subscribe({
                                logger.i(TAG, "Request success")
                                if (isResponseValid(it)) {
                                    logger.w(TAG, "Link might be not valid anymore")
                                    issueReporter.report("Link might be not valid anymore")
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

    private companion object {

        private fun isResponseValid(message: String) =
                "niepoprawny link".toRegex().find(message.toLowerCase()) != null
    }
}