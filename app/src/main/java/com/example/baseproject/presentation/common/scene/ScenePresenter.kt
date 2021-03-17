package com.example.baseproject.presentation.common.scene

import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate

abstract class ScenePresenter(sceneUi: SceneUi) : DisposableHolder by DisposableHolderDelegate() {
    init {

    }

    abstract fun handleViews()
}