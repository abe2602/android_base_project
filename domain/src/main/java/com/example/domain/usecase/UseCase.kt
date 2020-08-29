package com.example.domain.usecase

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single

//in = just consumed, never produced
abstract class SingleUseCase<in Request, Response>(
    private val observeOn: Scheduler,
    private val subscribeOn: Scheduler
) {
    internal abstract fun getRawSingle(params: Request): Single<Response>

    fun getSingle(request: Request): Single<Response> = getRawSingle(request)
        .observeOn(observeOn)
        .subscribeOn(subscribeOn)
}

abstract class CompletableUseCase<in Request>(
    private val observeOn: Scheduler,
    private val subscribeOn: Scheduler
) {

    internal abstract fun getRawCompletable(params: Request): Completable

    fun getCompletable(request: Request): Completable = getRawCompletable(request)
        .observeOn(observeOn)
        .subscribeOn(subscribeOn)
}