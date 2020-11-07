package com.example.baseproject.presentation.pokemonlist

import com.example.baseproject.presentation.common.scene.SceneUi
import com.example.domain.model.Pokemon
import io.reactivex.Observable

interface PokemonListUi : SceneUi {
    fun displayNewPageError()
    fun displayNewPageLoading()
    fun displayPokemonList(pokemonList: List<Pokemon>, totalFetchedItems: Int, totalItems: Int)
    fun dismissNewPageLoading()
    fun displayBlockingError()

    val onChoosePokemon: Observable<String>
    val onRequestMorePokemon: Observable<Unit>
    val onTryAgain: Observable<Unit>
}