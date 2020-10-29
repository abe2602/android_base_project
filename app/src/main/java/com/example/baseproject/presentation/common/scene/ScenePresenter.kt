package com.example.baseproject.presentation.common.scene

import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import io.reactivex.rxkotlin.addTo

abstract class ScenePresenter(sceneUi: SceneUi) : DisposableHolder by DisposableHolderDelegate() {
    init {
        sceneUi.onViewCreated.subscribe {
            handleViews()
        }.addTo(sceneUi.disposables)
    }

    abstract fun handleViews()
}