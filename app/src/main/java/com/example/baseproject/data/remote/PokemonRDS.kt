package com.example.baseproject.data.remote

import com.example.baseproject.data.remote.models.PokemonListRM
import io.reactivex.Single
import retrofit2.http.GET

interface PokemonRDS {
    @GET("pokemon?limit=151&offset=0")
    fun getPokemonList(): Single<PokemonListRM>
}