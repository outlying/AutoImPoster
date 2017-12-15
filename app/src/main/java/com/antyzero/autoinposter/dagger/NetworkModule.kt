package com.antyzero.autoinposter.dagger

import com.antyzero.autoinposter.domain.network.InPostCalls
import com.antyzero.autoinposter.domain.network.OkHttpInPostCalls
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .build()

    @Provides
    @Singleton
    fun provideInPostCalls(okHttpClient: OkHttpClient): InPostCalls = OkHttpInPostCalls(okHttpClient)

}