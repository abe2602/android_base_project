package com.example.domain.datarepository

import com.example.domain.model.Pokemon
import io.reactivex.Single

interface PokemonDataRepository {
    fun getPokemonList(): Single<List<Pokemon>>
}