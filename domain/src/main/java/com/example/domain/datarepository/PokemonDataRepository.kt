package com.example.domain.datarepository

import com.example.domain.model.PokemonInformation
import com.example.domain.model.PokemonList
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface PokemonDataRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonList>
    suspend fun getPokemonInformation(pokemonName: String): Flow<PokemonInformation>
    suspend fun catchPokemon(pokemonName: String): Flow<Unit>
    suspend fun releasePokemon(pokemonName: String): Flow<Unit>
    suspend fun getCaughtPokemonList(): Flow<List<String>>
}