package com.example.baseproject.presentation.pokemon_information

import com.example.domain.usecase.GetPokemonInformationParamsUC
import com.example.domain.usecase.GetPokemonInformationUC
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PokemonInformationPresenter @Inject constructor(
    private val getPokemonInformationUC: GetPokemonInformationUC,
    private val pokemonInformationUi: PokemonInformationUi

) {
    init {
        pokemonInformationUi.onViewCreated.subscribe {
            handleViews()
        }.addTo(pokemonInformationUi.disposables)
    }

    private fun handleViews() {

        pokemonInformationUi.onReceivedPokemonName.flatMapSingle { pokemonName ->
            getPokemonInformationUC.getSingle(GetPokemonInformationParamsUC(pokemonName = pokemonName))
        }.doOnNext {pokemonInformation ->
            pokemonInformationUi.displayPokemonInformation(pokemonInformation)
        }.subscribe().addTo(pokemonInformationUi.disposables)

    }
}