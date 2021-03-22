package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatchPokemonUC @Inject constructor(
    private val pokemonRepository: PokemonDataRepository
) : FlowUseCase<CatchPokemonParamsUC, Unit>() {

    override suspend fun getRawFlow(params: CatchPokemonParamsUC) : Flow<Unit>{
        return pokemonRepository.catchPokemon(params.pokemonName)
    }

}

data class CatchPokemonParamsUC(val pokemonName: String)