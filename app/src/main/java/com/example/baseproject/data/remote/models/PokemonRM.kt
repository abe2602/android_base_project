package com.example.baseproject.data.remote.models

import com.google.gson.annotations.SerializedName

data class PokemonRM(
    @SerializedName("name")
    val name: String
)