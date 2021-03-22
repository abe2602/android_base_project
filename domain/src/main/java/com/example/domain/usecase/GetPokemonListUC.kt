package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.model.Pokemon
import com.example.domain.model.PokemonList
import io.reactivex.Scheduler
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUC @Inject constructor(
    private val pokemonRepository: PokemonDataRepository
) : FlowUseCase<GetPokemonListUCParams, PokemonList>() {
    override suspend fun getRawFlow(params: GetPokemonListUCParams): Flow<PokemonList> =
        pokemonRepository.getPokemonList(params.limit, params.offset)
}

data class GetPokemonListUCParams(val limit: Int, val offset: Int)

