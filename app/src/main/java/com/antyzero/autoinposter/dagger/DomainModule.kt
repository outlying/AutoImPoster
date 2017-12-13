package com.antyzero.autoinposter.dagger

import com.antyzero.autoinposter.domain.InPostMessageDetector
import com.antyzero.autoinposter.domain.LinkExtractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideLinkExtractor(): LinkExtractor = LinkExtractor

    @Provides
    @Singleton
    fun provideInPostMessageDetector(linkExtractor: LinkExtractor): InPostMessageDetector =
            InPostMessageDetector(linkExtractor)
}