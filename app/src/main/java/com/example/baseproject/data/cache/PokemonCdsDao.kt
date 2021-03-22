package com.example.baseproject.data.cache

import androidx.room.*
import com.example.baseproject.data.cache.model.PokemonCM
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonCdsDao {
    @Query("SELECT * FROM pokemon_list")
    fun getPokemonList(): Flow<List<PokemonCM>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertPokemonList(pokemonList : List<PokemonCM>)

    @Delete
    fun deletePokemonFromList(pokemon: PokemonCM)
}