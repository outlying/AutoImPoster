package com.antyzero.autoinposter

import com.antyzero.autoinposter.dagger.AndroidModule
import dagger.Component


@Component(modules = [
    AndroidModule::class])
interface ApplicationComponent {
}