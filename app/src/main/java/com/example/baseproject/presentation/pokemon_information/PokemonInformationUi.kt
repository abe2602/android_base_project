package com.example.baseproject.presentation.pokemon_information

import com.example.baseproject.presentation.common.scene.SceneUi
import com.example.domain.model.PokemonInformation
import io.reactivex.Observable

interface PokemonInformationUi : SceneUi {
    fun displayPokemonInformation(pokemonInformation: PokemonInformation)

    val onReceivedPokemonName: Observable<String>
}