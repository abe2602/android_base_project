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

        Observable.merge(
            Observable.just(Unit),
            pokemonListUi.onTryAgain.filter { totalFetchedItems == 0 })
            .doOnNext {
                pokemonListUi.displayLoading()
            }.flatMapCompletable {
                getPokemonListUC.getSingle(GetPokemonListUCParams(limit, offset))
                    .doOnSuccess {
                        totalFetchedItems += it.pokemonList.size
                        pokemonListUi.displayPokemonList(
                            it.pokemonList,
                            totalFetchedItems,
                            it.total
                        )
                        offset = limit
                        limit += 30
                    }.doOnError {
                        pokemonListUi.displayBlockingError()
                    }.doFinally {
                        pokemonListUi.dismissLoading()
                    }.ignoreElement()
                    .onErrorComplete()
            }
            .onErrorComplete()
            .subscribe()
            .addTo(disposables)

        Observable.merge(
            pokemonListUi.onRequestMorePokemon,
            pokemonListUi.onTryAgain.filter { totalFetchedItems > 0 }
        ).doOnNext {
            isFetchingNewPage = true
            pokemonListUi.displayNewPageLoading()
        }.flatMapCompletable {
            getPokemonListUC.getSingle(GetPokemonListUCParams(limit, offset))
                .doOnSuccess {
                    totalFetchedItems = it.pokemonList.size
                    pokemonListUi.displayPokemonList(
                        it.pokemonList,
                        totalFetchedItems,
                        it.total
                    )
                    offset = limit
                    limit += 30
                }.doOnError {
                    pokemonListUi.displayNewPageError()
                }.doFinally {
                    isFetchingNewPage = false
                    pokemonListUi.dismissNewPageLoading()
                }.ignoreElement().onErrorComplete()
        }
            .onErrorComplete()
            .subscribe()
            .addTo(disposables)
    }
}