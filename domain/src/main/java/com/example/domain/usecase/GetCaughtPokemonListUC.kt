package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCaughtPokemonListUC @Inject constructor(
    private val pokemonRepository: PokemonDataRepository
) : FlowUseCase<Unit, List<String>>() {
    override suspend fun getRawFlow(params: Unit): Flow<List<String>> =
        pokemonRepository.getCaughtPokemonList()
}
