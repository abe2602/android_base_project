package com.example.baseproject.presentation.pokemonlist

import com.example.baseproject.presentation.common.scene.SceneUi
import com.example.domain.model.Pokemon
import io.reactivex.Observable

interface PokemonListUi : SceneUi {

    fun displayPokemonList(pokemonList: List<Pokemon>)

    val onChoosePokemon: Observable<String>
}