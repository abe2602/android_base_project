package com.example.baseproject.presentation.common

open class StateEvent<out T>

class ViewModelSuccess<out T>(private val data: T) : StateEvent<T>() {
    fun getData() : T = data
}

class ViewModelError<out T>(error: Throwable) : StateEvent<T>()

class ViewModelLoading<out T> : StateEvent<T>()

class ViewModelDismissLoading<out T> : StateEvent<T>()