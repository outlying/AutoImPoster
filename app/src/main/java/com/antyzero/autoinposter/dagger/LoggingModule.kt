package com.antyzero.autoinposter.dagger

import com.antyzero.autoinposter.AndroidLogger
import com.antyzero.autoinposter.domain.logger.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoggingModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger = Logger.apply {
        add(AndroidLogger)
    }
}