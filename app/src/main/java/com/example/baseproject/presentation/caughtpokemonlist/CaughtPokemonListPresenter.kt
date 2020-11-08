package com.example.baseproject.presentation.caughtpokemonlist

import com.example.baseproject.common.CatchPokemonDataObservable
import com.example.baseproject.presentation.common.scene.ScenePresenter
import com.example.domain.usecase.GetCaughtPokemonListUC
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class CaughtPokemonListPresenter @Inject constructor(
    private val caughtPokemonListUi: CaughtPokemonListUi,
    private val getCaughtPokemonListUC: GetCaughtPokemonListUC,
    @CatchPokemonDataObservable private val catchPokemonDataObservable: Observable<Unit>
) :
    ScenePresenter(caughtPokemonListUi) {

    override fun handleViews() {
        caughtPokemonListUi.displayLoading()

        Observable.merge(catchPokemonDataObservable, Observable.just(Unit)).flatMapSingle {
            getCaughtPokemonListUC.getSingle(Unit).doOnSuccess { caughtPokemonList ->
                if(caughtPokemonList.isEmpty()) {
                    caughtPokemonListUi.displayNoCaughtPokemonError()
                } else {
                    caughtPokemonListUi.displayCaughtPokemonList(caughtPokemonList)
                }
            }.doFinally {
                caughtPokemonListUi.dismissLoading()
            }
        }.subscribe().addTo(caughtPokemonListUi.disposables)
    }
}