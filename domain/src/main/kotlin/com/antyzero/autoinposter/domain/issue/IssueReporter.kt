package com.antyzero.autoinposter.domain.issue


interface IssueReporter {

    fun report(throwable: Throwable, message: String)

    fun report(message: String) {
        report(IllegalStateException(message), message)
    }

    fun report(throwable: Throwable) {
        report(throwable, throwable.message ?: "")
    }
}