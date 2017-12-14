package com.antyzero.autoinposter.dagger

import com.antyzero.autoinposter.logger.AndroidLogger
import com.antyzero.autoinposter.logger.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoggerModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger = Logger.apply {
        add(AndroidLogger)
    }
}