package com.example.baseproject.presentation.pokemoninformation

import com.example.baseproject.presentation.common.scene.SceneUi
import com.example.domain.model.PokemonInformation
import io.reactivex.Observable

interface PokemonInformationUi : SceneUi {
    fun displayPokemonInformation(pokemonInformation: PokemonInformation)
    fun displayBlockingError()

    val onTryAgain: Observable<Unit>
    val onReceivedPokemonName: Observable<String>
    val onCatchPokemon: Observable<String>
    val onReleasePokemon: Observable<String>
}