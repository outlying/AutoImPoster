package com.antyzero.autoinposter.dagger

import com.antyzero.autoinposter.network.InPostCalls
import com.antyzero.autoinposter.network.OkHttpInPostCalls
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    @Singleton
    fun provideInPostCalls(okHttpClient: OkHttpClient): InPostCalls = OkHttpInPostCalls(okHttpClient)

}