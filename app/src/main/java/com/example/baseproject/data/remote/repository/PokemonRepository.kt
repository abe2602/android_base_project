package com.example.baseproject.data.remote.repository

import com.example.baseproject.data.remote.PokemonRDS
import com.example.baseproject.data.remote.mappers.toDM
import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonInformation
import io.reactivex.Single
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokemonRDS: PokemonRDS) :
    PokemonDataRepository {
    override fun getPokemonList(): Single<List<Pokemon>> =
        pokemonRDS.getPokemonList().map {
            it.pokemonList.toDM()
        }

    override fun getPokemonInformation(pokemonName: String): Single<PokemonInformation> =
        pokemonRDS.getPokemonInformation(pokemonName).map {
            it.toDM()
        }

}