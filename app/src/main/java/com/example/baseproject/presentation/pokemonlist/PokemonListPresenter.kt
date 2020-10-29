package com.example.baseproject.presentation.pokemonlist

import com.example.baseproject.presentation.common.scene.ScenePresenter
import com.example.domain.usecase.GetPokemonListUC
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PokemonListPresenter @Inject constructor(
    private val pokemonListUi: PokemonListUi,
    private val getPokemonListUC: GetPokemonListUC
) : ScenePresenter(pokemonListUi) {

    override fun handleViews() {
        pokemonListUi.displayLoading()
        getPokemonListUC.getSingle(Unit).doOnSuccess {
            pokemonListUi.displayPokemonList(it)
        }.doFinally {
            pokemonListUi.dismissLoading()
        }.subscribe().addTo(pokemonListUi.disposables)
    }
}