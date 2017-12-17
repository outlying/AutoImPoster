package com.antyzero.autoinposter.logger

import com.antyzero.autoinposter.domain.issue.IssueReporter
import com.antyzero.autoinposter.domain.logger.Logger
import com.crashlytics.android.Crashlytics


class CrashlyticsIssueReporter(
        private val logger: Logger,
        private val crashlytics: Crashlytics) : IssueReporter {

    override fun report(throwable: Throwable, message: String) {
        logger.e("Non-fatal issue", message, throwable)
        crashlytics.core.logException(throwable)
    }
}