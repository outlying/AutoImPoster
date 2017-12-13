package com.antyzero.autoinposter

import com.antyzero.autoinposter.dagger.AndroidModule
import com.antyzero.autoinposter.dagger.DomainModule
import dagger.Component


@Component(modules = [
    DomainModule::class,
    AndroidModule::class])

interface ApplicationComponent {
}