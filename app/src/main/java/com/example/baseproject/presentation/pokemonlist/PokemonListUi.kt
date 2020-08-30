package com.example.baseproject.presentation.pokemonlist

import android.view.View
import android.view.ViewGroup
import com.example.domain.model.Pokemon
import io.reactivex.Observable

interface PokemonListUi {
    // Vai virar interface pai!
    fun buildContainerView(parentViewGroup: ViewGroup?): View

    fun displayPokemonList(pokemonList: List<Pokemon>)
    fun loadComponents()

    val onPokemonClick: Observable<Unit>
}