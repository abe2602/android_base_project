package com.example.baseproject.presentation.common

import android.app.Application

class PokedexApplication: Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule())
            .build()
    }
}