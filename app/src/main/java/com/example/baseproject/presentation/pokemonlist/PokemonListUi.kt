package com.example.baseproject.presentation.pokemonlist

import com.example.baseproject.common.DisposableHolder
import com.example.domain.model.Pokemon
import io.reactivex.Observable

interface PokemonListUi : DisposableHolder {

    fun displayPokemonList(pokemonList: List<Pokemon>)

    val onChoosePokemon: Observable<String>
    val onViewCreated: Observable<Unit>

}