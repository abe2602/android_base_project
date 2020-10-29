package com.example.baseproject.data.remote.models

import com.google.gson.annotations.SerializedName

data class PokemonInformationRM(
    @SerializedName("name")
    val name: String,
    @SerializedName("sprites")
    val pokemonSprites: SpritesRM
)