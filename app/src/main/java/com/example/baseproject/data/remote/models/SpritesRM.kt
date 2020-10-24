package com.example.baseproject.data.remote.models

import com.google.gson.annotations.SerializedName

data class SpritesRM(
    @SerializedName("front_default")
    val frontSprite: String,
    @SerializedName("back_default")
    val backSprite: String
)