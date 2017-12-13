package com.antyzero.autoinposter

import com.antyzero.autoinposter.dagger.AndroidModule
import com.antyzero.autoinposter.dagger.DomainModule
import com.antyzero.autoinposter.receiver.SmsReceiver
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    DomainModule::class,
    AndroidModule::class])
interface ApplicationComponent {

    fun inject(smsReceiver: SmsReceiver)
}