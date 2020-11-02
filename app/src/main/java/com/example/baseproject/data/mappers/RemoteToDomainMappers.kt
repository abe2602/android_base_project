package com.example.baseproject.data.mappers

import com.example.baseproject.data.remote.models.PokemonInformationRM
import com.example.baseproject.data.remote.models.PokemonListRM
import com.example.baseproject.data.remote.models.PokemonRM
import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonInformation
import com.example.domain.model.PokemonList

fun PokemonRM.toDM() = Pokemon(name = name)

fun List<PokemonRM>.toDM() = this.map { it.toDM() }

fun PokemonListRM.toDM() = PokemonList(pokemonList = pokemonList.toDM(), total = total)

fun PokemonInformationRM.toDM(caughtPokemon: Boolean) = PokemonInformation(
    name = name,
    frontSprite = pokemonSprites.frontSprite,
    backSprite = pokemonSprites.backSprite,
    caughtPokemon = caughtPokemon
)