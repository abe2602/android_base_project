package com.example.baseproject.presentation.pokemon_information

import com.example.baseproject.presentation.common.scene.ScenePresenter
import com.example.domain.usecase.GetPokemonInformationParamsUC
import com.example.domain.usecase.GetPokemonInformationUC
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PokemonInformationPresenter @Inject constructor(
    private val getPokemonInformationUC: GetPokemonInformationUC,
    private val pokemonInformationUi: PokemonInformationUi
) : ScenePresenter(pokemonInformationUi) {

    override fun handleViews() {
        pokemonInformationUi.onReceivedPokemonName.doOnNext {
            pokemonInformationUi.displayLoading()
        }.flatMapSingle { pokemonName ->
            getPokemonInformationUC.getSingle(GetPokemonInformationParamsUC(pokemonName = pokemonName))
        }.doOnNext { pokemonInformation ->
            pokemonInformationUi.displayPokemonInformation(pokemonInformation)
            pokemonInformationUi.dismissLoading()
        }.subscribe().addTo(pokemonInformationUi.disposables)
    }
}