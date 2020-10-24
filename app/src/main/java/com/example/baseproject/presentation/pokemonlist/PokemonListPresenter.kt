package com.example.baseproject.presentation.pokemonlist

import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.domain.usecase.GetPokemonListUC
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PokemonListPresenter @Inject constructor(
    private val pokemonListUi: PokemonListUi,
    private val getPokemonListUC: GetPokemonListUC
) : DisposableHolder by DisposableHolderDelegate() {

    init {
        pokemonListUi.onViewCreated.subscribe{
            handleView()
        }.addTo(pokemonListUi.disposables)
    }

    private fun handleView() {
        getPokemonListUC.getSingle(Unit).doOnSuccess {
            pokemonListUi.displayPokemonList(it)
        }.subscribe().addTo(pokemonListUi.disposables)
    }
}