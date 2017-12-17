package com.antyzero.autoinposter

import com.antyzero.autoinposter.dagger.*
import com.antyzero.autoinposter.receiver.SmsReceiver
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    DomainModule::class,
    AndroidModule::class,
    NetworkModule::class,
    FabricModule::class,
    LoggingModule::class])
interface ApplicationComponent {

    fun inject(smsReceiver: SmsReceiver)
}