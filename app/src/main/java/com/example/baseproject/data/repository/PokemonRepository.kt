package com.example.baseproject.data.repository

import com.example.baseproject.data.cache.PokemonCdsDao
import com.example.baseproject.data.cache.model.PokemonCM
import com.example.baseproject.data.mappers.toDM
import com.example.baseproject.data.remote.PokemonRDS
import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.model.PokemonInformation
import com.example.domain.model.PokemonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonRDS: PokemonRDS,
    private val pokemonCdsDao: PokemonCdsDao,
) : PokemonDataRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonList> =
        flow {
            val pokemonList = pokemonRDS.getPokemonList(limit, offset)

            emit(pokemonList.toDM())
        }

    override suspend fun getPokemonInformation(pokemonName: String): Flow<PokemonInformation> =
        getCaughtPokemonList().flatMapMerge { caughtPokemonList ->
            return@flatMapMerge flow {
                val pokemonInformation = pokemonRDS.getPokemonInformation(pokemonName)
                emit(pokemonInformation.toDM(caughtPokemonList.contains(pokemonInformation.name)))
            }
        }

    override suspend fun catchPokemon(pokemonName: String): Flow<Unit> {
        return pokemonCdsDao.getPokemonList().map {
            val newPokemonList = it.toMutableList()
            newPokemonList.add(PokemonCM(pokemonName))
            pokemonCdsDao.upsertPokemonList(newPokemonList)
        }
    }


    override suspend fun releasePokemon(pokemonName: String): Flow<Unit > = flow {
        pokemonCdsDao.deletePokemonFromList(PokemonCM(pokemonName))
        emit(Unit)
    }

    override suspend fun getCaughtPokemonList(): Flow<List<String>> = pokemonCdsDao.getPokemonList()
        .map {
            it.map { pokemon -> pokemon.name }
        }
}