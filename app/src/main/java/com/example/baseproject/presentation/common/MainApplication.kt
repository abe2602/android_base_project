package com.example.baseproject.presentation.common

import android.app.Application
import com.pacoworks.rxpaper2.RxPaperBook

class MainApplication: Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}