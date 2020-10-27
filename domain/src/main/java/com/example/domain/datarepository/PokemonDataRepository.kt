package com.example.domain.datarepository

import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonInformation
import io.reactivex.Completable
import io.reactivex.Single

interface PokemonDataRepository {
    fun getPokemonList(): Single<List<Pokemon>>
    fun getPokemonInformation(pokemonName: String): Single<PokemonInformation>
    fun catchPokemon(pokemonName: String): Completable
    fun getCaughtPokemonList(): Single<List<String>>
}