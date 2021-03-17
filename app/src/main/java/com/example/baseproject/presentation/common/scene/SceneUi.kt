package com.example.baseproject.presentation.common.scene

import com.example.baseproject.common.DisposableHolder

interface SceneUi : DisposableHolder {
    val viewModel: SceneViewModel

    fun displayLoading()
    fun dismissLoading()
}