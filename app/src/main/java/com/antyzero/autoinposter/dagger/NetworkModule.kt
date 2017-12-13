package com.antyzero.autoinposter.dagger

import dagger.Module
import okhttp3.OkHttpClient


@Module
class NetworkModule {

    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()
}