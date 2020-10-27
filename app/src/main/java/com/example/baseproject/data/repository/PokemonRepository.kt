package com.example.baseproject.data.repository

import com.example.baseproject.data.cache.PokemonCDS
import com.example.baseproject.data.remote.PokemonRDS
import com.example.baseproject.data.mappers.toDM
import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonInformation
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonRDS: PokemonRDS,
    private val pokemonCDS: PokemonCDS
) :
    PokemonDataRepository {
    override fun getPokemonList(): Single<List<Pokemon>> =
        pokemonRDS.getPokemonList().map {
            it.pokemonList.toDM()
        }

    override fun getPokemonInformation(pokemonName: String): Single<PokemonInformation> =
        pokemonRDS.getPokemonInformation(pokemonName).map {
            it.toDM()
        }

    override fun catchPokemon(pokemonName: String): Completable = pokemonCDS.getCaughtPokemonList()
        .flatMapCompletable { caughtPokemonList ->
            caughtPokemonList.add(pokemonName)
            pokemonCDS.catchPokemon(caughtPokemonList)
        }

    override fun getCaughtPokemonList(): Single<List<String>> =
        pokemonCDS.getCaughtPokemonList().map {
            it.toList()
        }
}