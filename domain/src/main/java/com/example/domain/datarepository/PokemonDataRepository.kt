package com.example.domain.datarepository

import com.example.domain.model.PokemonInformation
import com.example.domain.model.PokemonList
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface PokemonDataRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonList>
    fun getPokemonInformation(pokemonName: String): Single<PokemonInformation>
    fun catchPokemon(pokemonName: String): Completable
    fun releasePokemon(pokemonName: String): Completable
    fun getCaughtPokemonList(): Single<List<String>>
}