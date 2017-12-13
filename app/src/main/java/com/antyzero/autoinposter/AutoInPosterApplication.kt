package com.antyzero.autoinposter

import android.app.Application


class AutoInPosterApplication : Application() {

    val applicationComponent:ApplicationComponent by lazy {
        Any() as ApplicationComponent
    }
}