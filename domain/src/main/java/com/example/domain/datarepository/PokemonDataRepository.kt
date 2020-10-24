package com.example.domain.datarepository

import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonInformation
import io.reactivex.Single

interface PokemonDataRepository {
    fun getPokemonList(): Single<List<Pokemon>>
    fun getPokemonInformation(pokemonName: String): Single<PokemonInformation>
}