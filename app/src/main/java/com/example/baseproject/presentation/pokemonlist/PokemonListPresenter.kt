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
    private var limit: Int = 30
    private var offset: Int = 0
    private var totalFetchedItems = 0
    private var isFetchingNewPage = false

    override fun handleViews() {
        Observable.just(Unit)
            .doOnNext {
                pokemonListUi.displayLoading()
            }.flatMapSingle {
                getPokemonListUC.getSingle(GetPokemonListUCParams(limit, offset))
                    .doOnSuccess {
                        totalFetchedItems += it.pokemonList.size
                        pokemonListUi.displayPokemonList(
                            it.pokemonList,
                            totalFetchedItems,
                            it.total
                        )
                    }.doFinally {
                        pokemonListUi.dismissLoading()
                    }
            }.ignoreElements().onErrorComplete().subscribe().addTo(pokemonListUi.disposables)
        
        Observable.merge(pokemonListUi.onRequestMorePokemon, pokemonListUi.onTryAgain)
            .doOnNext {
                offset = limit
                limit += 30
                isFetchingNewPage = true

                pokemonListUi.displayNewPageLoading()
            }.flatMapSingle {
                getPokemonListUC.getSingle(GetPokemonListUCParams(limit, offset))
                    .doOnSuccess {
                        totalFetchedItems = it.pokemonList.size
                        pokemonListUi.displayPokemonList(
                            it.pokemonList,
                            totalFetchedItems,
                            it.total
                        )
                    }.doOnError {
                        pokemonListUi.displayNoInternetError()
                    }.doFinally {
                        isFetchingNewPage = false
                        pokemonListUi.dismissNewPageLoading()
                    }
            }.ignoreElements().onErrorComplete().subscribe().addTo(pokemonListUi.disposables)
    }
}