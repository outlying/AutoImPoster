package com.antyzero.autoinposter

import android.app.Application
import com.antyzero.autoinposter.dagger.AndroidModule


class AutoInPosterApplication : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .build()
    }
}