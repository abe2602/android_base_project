package com.example.baseproject.data.remote.mappers

import com.example.baseproject.data.remote.models.PokemonRM
import com.example.domain.model.Pokemon

fun PokemonRM.toDM() = Pokemon(name = name)

fun List<PokemonRM>.toDM() = this.map { it.toDM() }