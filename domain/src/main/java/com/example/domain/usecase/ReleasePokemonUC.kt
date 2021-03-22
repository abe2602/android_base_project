package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReleasePokemonUC @Inject constructor(
    private val pokemonRepository: PokemonDataRepository
) : FlowUseCase<ReleasePokemonUCParams, Unit>() {
    override suspend fun getRawFlow(params: ReleasePokemonUCParams): Flow<Unit> =
        pokemonRepository.releasePokemon(params.pokemonName)
}

data class ReleasePokemonUCParams(val pokemonName: String)