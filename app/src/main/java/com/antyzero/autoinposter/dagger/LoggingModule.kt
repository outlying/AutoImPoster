package com.antyzero.autoinposter.dagger

import com.antyzero.autoinposter.AndroidLogger
import com.antyzero.autoinposter.BuildConfig
import com.antyzero.autoinposter.domain.issue.IssueReporter
import com.antyzero.autoinposter.domain.logger.Logger
import com.antyzero.autoinposter.logger.CrashlyticsIssueReporter
import com.antyzero.autoinposter.logger.CrashlyticsLogger
import com.crashlytics.android.Crashlytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoggingModule {

    @Provides
    @Singleton
    fun provideLogger(crashlytics: Crashlytics): Logger = Logger.apply {

        if (BuildConfig.DEBUG) {
            add(AndroidLogger)
        } else {
            add(CrashlyticsLogger(crashlytics))
        }
    }

    @Provides
    @Singleton
    fun provideIssueReporter(logger: Logger, crashlytics: Crashlytics): IssueReporter {
        return CrashlyticsIssueReporter(logger, crashlytics)
    }
}