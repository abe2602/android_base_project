package com.example.baseproject.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_list")
data class PokemonCM(
    @PrimaryKey(autoGenerate = false)
    var name: String
)