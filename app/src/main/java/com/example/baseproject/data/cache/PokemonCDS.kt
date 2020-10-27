package com.example.baseproject.data.cache

import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PokemonCDS @Inject constructor(private val rxPaper: RxPaperBook) {
    companion object {
        const val CAUGHT_POKEMON_LIST_KEY = "CAUGHT_POKEMON_LIST"
    }

    fun getCaughtPokemonList(): Single<MutableList<String>> =
        rxPaper.read<MutableList<String>>(CAUGHT_POKEMON_LIST_KEY).onErrorReturn { mutableListOf() }

    fun catchPokemon(caughtPokemonList: List<String>): Completable = rxPaper.write(CAUGHT_POKEMON_LIST_KEY, caughtPokemonList)
}