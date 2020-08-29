package com.example.baseproject.data.remote.models

import com.google.gson.annotations.SerializedName

data class PokemonListRM(
    @SerializedName("previous")
    val previousPage: Int?,

    @SerializedName("next")
    val nextPageUrl: String?,

    @SerializedName("count")
    val total: Int,

    @SerializedName("results")
    val pokemonList: List<PokemonRM>
)