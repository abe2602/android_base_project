package com.example.baseproject.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.baseproject.data.cache.model.PokemonCM

@Database(entities = [PokemonCM::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonCdsDao(): PokemonCdsDao
}