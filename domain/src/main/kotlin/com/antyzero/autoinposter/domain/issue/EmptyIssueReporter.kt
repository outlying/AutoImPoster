package com.antyzero.autoinposter.domain.issue

object EmptyIssueReporter : IssueReporter {

    override fun report(throwable: Throwable, message: String) {
        // do nothing
    }
}