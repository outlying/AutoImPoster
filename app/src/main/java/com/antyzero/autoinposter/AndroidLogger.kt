package com.antyzero.autoinposter

import android.util.Log
import com.antyzero.autoinposter.domain.logger.Logger


object AndroidLogger : Logger {

    override fun v(tag: String, message: String, throwable: Throwable?) {
        Log.v(tag, message, throwable)
    }

    override fun i(tag: String, message: String, throwable: Throwable?) {
        Log.i(tag, message, throwable)
    }

    override fun d(tag: String, message: String, throwable: Throwable?) {
        Log.d(tag, message, throwable)
    }

    override fun w(tag: String, message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    override fun wtf(tag: String, message: String, throwable: Throwable?) {
        Log.wtf(tag, message, throwable)
    }
}