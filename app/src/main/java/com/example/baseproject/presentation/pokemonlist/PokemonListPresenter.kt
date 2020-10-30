package com.example.baseproject.presentation.pokemonlist

import com.example.baseproject.presentation.common.scene.ScenePresenter
import com.example.domain.usecase.GetPokemonListUC
import com.example.domain.usecase.GetPokemonListUCParams
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PokemonListPresenter @Inject constructor(
    private val pokemonListUi: PokemonListUi,
    private val getPokemonListUC: GetPokemonListUC
) : ScenePresenter(pokemonListUi) {
    private var limit: Int = 0
    private var offset: Int = 0
    private var totalFetchedItems = 0

    override fun handleViews() {
        pokemonListUi.displayLoading()

        Observable.merge(pokemonListUi.onRequestMorePokemon, Observable.just(Unit))
            .doOnNext {
                if(offset == 0 && limit != 0) {
                    offset = limit
                } else {
                    offset = limit
                    limit += 30
                }
            }.flatMapSingle {
                getPokemonListUC.getSingle(GetPokemonListUCParams(limit, offset))
                    .doOnSuccess {
                        totalFetchedItems += it.size
                        pokemonListUi.displayPokemonList(it, totalFetchedItems)
                    }.doOnError {
                      //  pokemonListUi.displayNoInternetError()
                    }.doFinally {
                        pokemonListUi.dismissLoading()
                    }
            }.ignoreElements().onErrorComplete().subscribe().addTo(pokemonListUi.disposables)
    }
}