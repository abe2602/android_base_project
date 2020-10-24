package com.example.baseproject.data.remote

import com.example.baseproject.data.remote.models.PokemonInformationRM
import com.example.baseproject.data.remote.models.PokemonListRM
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonRDS {
    @GET("pokemon?limit=151&offset=0")
    fun getPokemonList(): Single<PokemonListRM>

    @GET("pokemon/{pokemonName}")
    fun getPokemonInformation(@Path("pokemonName") pokemonName: String): Single<PokemonInformationRM>
}