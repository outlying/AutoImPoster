package com.antyzero.autoinposter.domain.logger

interface Logger {

    fun v(tag: String, message: String, throwable: Throwable? = null)

    fun d(tag: String, message: String, throwable: Throwable? = null)

    fun i(tag: String, message: String, throwable: Throwable? = null)

    fun w(tag: String, message: String, throwable: Throwable? = null)

    fun e(tag: String, message: String, throwable: Throwable? = null)

    fun wtf(tag: String, message: String, throwable: Throwable? = null)

    companion object : Logger {

        private val loggers = mutableListOf<Logger>()

        fun add(logger: Logger) = loggers.add(logger)

        override fun v(tag: String, message: String, throwable: Throwable?) {
            loggers.forEach { it.v(tag, message, throwable) }
        }

        override fun d(tag: String, message: String, throwable: Throwable?) {
            loggers.forEach { it.d(tag, message, throwable) }
        }

        override fun i(tag: String, message: String, throwable: Throwable?) {
            loggers.forEach { it.i(tag, message, throwable) }
        }

        override fun w(tag: String, message: String, throwable: Throwable?) {
            loggers.forEach { it.w(tag, message, throwable) }
        }

        override fun e(tag: String, message: String, throwable: Throwable?) {
            loggers.forEach { it.e(tag, message, throwable) }
        }

        override fun wtf(tag: String, message: String, throwable: Throwable?) {
            loggers.forEach { it.wtf(tag, message, throwable) }
        }
    }
}