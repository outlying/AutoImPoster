package com.antyzero.autoinposter.logger

import android.util.Log
import com.antyzero.autoinposter.domain.logger.Logger
import com.crashlytics.android.Crashlytics


class CrashlyticsLogger(private val crashlytics: Crashlytics) : Logger {

    override fun v(tag: String, message: String, throwable: Throwable?) {
        crashlytics.core.log(Log.DEBUG, tag, message)
    }

    override fun d(tag: String, message: String, throwable: Throwable?) {
        crashlytics.core.log(Log.DEBUG, tag, message)
    }

    override fun i(tag: String, message: String, throwable: Throwable?) {
        crashlytics.core.log(Log.INFO, tag, message)
    }

    override fun w(tag: String, message: String, throwable: Throwable?) {
        crashlytics.core.log(Log.WARN, tag, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        crashlytics.core.log(Log.ERROR, tag, message)
    }

    override fun wtf(tag: String, message: String, throwable: Throwable?) {
        crashlytics.core.log(Int.MAX_VALUE, tag, message)
    }
}