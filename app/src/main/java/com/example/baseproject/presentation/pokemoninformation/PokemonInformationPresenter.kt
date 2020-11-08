package com.example.baseproject.presentation.pokemoninformation

import com.example.baseproject.presentation.common.scene.ScenePresenter
import com.example.domain.usecase.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PokemonInformationPresenter @Inject constructor(
    private val pokemonInformationUi: PokemonInformationUi,
    private val getPokemonInformationUC: GetPokemonInformationUC,
    private val catchPokemonUC: CatchPokemonUC,
    private val releasePokemonUC: ReleasePokemonUC
) : ScenePresenter(pokemonInformationUi) {
    private lateinit var pokemonName: String

    override fun handleViews() {
        Observable.merge(
            pokemonInformationUi.onReceivedPokemonName,
            pokemonInformationUi.onTryAgain.map {
                pokemonName
            }).doOnNext {
            pokemonName = it
            pokemonInformationUi.displayLoading()
        }.flatMapCompletable { pokemonName ->
            getPokemonInformationUC.getSingle(GetPokemonInformationParamsUC(pokemonName = pokemonName))
                .doOnSuccess { pokemonInformation ->
                    pokemonInformationUi.displayPokemonInformation(pokemonInformation)
                }.doOnError {
                    pokemonInformationUi.displayBlockingError()
                }.doFinally {
                    pokemonInformationUi.dismissLoading()
                }.ignoreElement().onErrorComplete()
        }.subscribe()
            .addTo(pokemonInformationUi.disposables)

        pokemonInformationUi.onCatchPokemon.flatMapCompletable {
            catchPokemonUC.getCompletable(CatchPokemonParamsUC(pokemonName = it))
        }.subscribe().addTo(pokemonInformationUi.disposables)

        pokemonInformationUi.onReleasePokemon.flatMapCompletable {
            releasePokemonUC.getCompletable(ReleasePokemonUCParams(pokemonName = it))
        }.subscribe().addTo(pokemonInformationUi.disposables)
    }
}