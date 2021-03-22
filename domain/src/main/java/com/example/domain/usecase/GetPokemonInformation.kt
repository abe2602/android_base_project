package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.model.PokemonInformation
import io.reactivex.Scheduler
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonInformationUC @Inject constructor(
    private val pokemonRepository: PokemonDataRepository
) : FlowUseCase<GetPokemonInformationParamsUC, PokemonInformation>() {
    override suspend fun getRawFlow(params: GetPokemonInformationParamsUC): Flow<PokemonInformation> =
        pokemonRepository.getPokemonInformation(params.pokemonName)
}

data class GetPokemonInformationParamsUC(val pokemonName: String)
