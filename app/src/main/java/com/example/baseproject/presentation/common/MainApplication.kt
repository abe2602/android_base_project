package com.example.baseproject.presentation.common

import android.app.Application

class MainApplication: Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule())
            .build()
    }
}