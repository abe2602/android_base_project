package com.example.baseproject.presentation.pokemoninformation

import com.example.baseproject.presentation.common.scene.ScenePresenter
import com.example.domain.usecase.*
import io.reactivex.Completable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PokemonInformationPresenter @Inject constructor(
    private val pokemonInformationUi: PokemonInformationUi,
    private val getPokemonInformationUC: GetPokemonInformationUC,
    private val catchPokemonUC: CatchPokemonUC,
    private val releasePokemonUC: ReleasePokemonUC
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

        pokemonInformationUi.onCatchPokemon.flatMapCompletable {
            catchPokemonUC.getCompletable(CatchPokemonParamsUC(pokemonName = it))
        }.subscribe().addTo(pokemonInformationUi.disposables)

        pokemonInformationUi.onReleasePokemon.flatMapCompletable {
            releasePokemonUC.getCompletable(ReleasePokemonUCParams(pokemonName = it))
        }.subscribe().addTo(pokemonInformationUi.disposables)
    }
}