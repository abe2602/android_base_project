package com.example.baseproject.presentation.caughtpokemonlist

import com.example.baseproject.presentation.common.scene.ScenePresenter
import com.example.domain.usecase.GetCaughtPokemonListUC
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class CaughtPokemonListPresenter @Inject constructor(
    private val caughtPokemonListUi: CaughtPokemonListUi,
    private val getCaughtPokemonListUC: GetCaughtPokemonListUC
) :
    ScenePresenter(caughtPokemonListUi) {

    override fun handleViews() {
        caughtPokemonListUi.displayLoading()

        getCaughtPokemonListUC.getSingle(Unit).doOnSuccess {caughtPokemonList ->
            caughtPokemonListUi.displayCaughtPokemonList(caughtPokemonList)
        }.doFinally {
            caughtPokemonListUi.dismissLoading()
        }.subscribe().addTo(caughtPokemonListUi.disposables)
    }
}