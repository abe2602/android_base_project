package com.example.baseproject.presentation.pokemon_information

import com.example.baseproject.common.DisposableHolder
import com.example.domain.model.PokemonInformation
import io.reactivex.Observable

interface PokemonInformationUi : DisposableHolder {
    fun displayPokemonInformation(pokemonInformation: PokemonInformation)

    val onViewCreated: Observable<Unit>
    val onReceivedPokemonName: Observable<String>
}