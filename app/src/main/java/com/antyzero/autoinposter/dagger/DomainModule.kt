package com.antyzero.autoinposter.dagger

import com.antyzero.autoinposter.BuildConfig
import com.antyzero.autoinposter.domain.ImPoster
import com.antyzero.autoinposter.domain.InPostMessageDetector
import com.antyzero.autoinposter.domain.LinkExtractor
import com.antyzero.autoinposter.domain.issue.IssueReporter
import com.antyzero.autoinposter.domain.network.InPostCalls
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideLinkExtractor(): LinkExtractor = LinkExtractor

    @Provides
    @Singleton
    fun provideInPostMessageDetector(linkExtractor: LinkExtractor): InPostMessageDetector {
        return if (BuildConfig.DEBUG) {
            val acceptAnyNumber: (String) -> Boolean = { true }
            InPostMessageDetector(linkExtractor, acceptAnyNumber)
        } else {
            InPostMessageDetector(linkExtractor)
        }
    }

    @Provides
    @Singleton
    fun provideImPoster(
            inPostMessageDetector: InPostMessageDetector,
            linkExtractor: LinkExtractor,
            issueReporter: IssueReporter,
            inPostCalls: InPostCalls): ImPoster {
        return ImPoster(
                inPostMessageDetector = inPostMessageDetector,
                inPostCalls = inPostCalls,
                linkExtractor = linkExtractor,
                issueReporter = issueReporter,
                receivingSchedulers = AndroidSchedulers.mainThread())
    }

}