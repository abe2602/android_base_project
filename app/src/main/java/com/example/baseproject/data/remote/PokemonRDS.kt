package com.example.baseproject.data.remote

import com.example.baseproject.data.remote.models.PokemonInformationRM
import com.example.baseproject.data.remote.models.PokemonListRM
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonRDS {
    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): Single<PokemonListRM>

    @GET("pokemon/{pokemonName}")
    fun getPokemonInformation(@Path("pokemonName") pokemonName: String): Single<PokemonInformationRM>
}