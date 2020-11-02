package com.example.domain.datarepository

import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonInformation
import com.example.domain.model.PokemonList
import io.reactivex.Completable
import io.reactivex.Single

interface PokemonDataRepository {
    fun getPokemonList(limit: Int, offset: Int): Single<PokemonList>
    fun getPokemonInformation(pokemonName: String): Single<PokemonInformation>
    fun catchPokemon(pokemonName: String): Completable
    fun releasePokemon(pokemonName: String): Completable
    fun getCaughtPokemonList(): Single<List<String>>
}