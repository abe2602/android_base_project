package com.example.baseproject.presentation.common.scene

import com.example.baseproject.common.DisposableHolder
import io.reactivex.Observable

interface SceneUi : DisposableHolder {
    val onViewCreated: Observable<Unit>
}