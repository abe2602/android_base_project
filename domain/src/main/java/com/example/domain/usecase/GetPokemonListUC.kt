package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.model.Pokemon
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetPokemonListUC @Inject constructor(
    @BackgroundScheduler backgroundScheduler: Scheduler,
    @MainScheduler mainScheduler: Scheduler,
    private val pokemonRepository: PokemonDataRepository
) : SingleUseCase<GetPokemonListUCParams, List<Pokemon>>(
    observeOn = mainScheduler,
    subscribeOn = backgroundScheduler
) {
    override fun getRawSingle(params: GetPokemonListUCParams): Single<List<Pokemon>> =
        pokemonRepository.getPokemonList(params.limit, params.offset)
}

data class GetPokemonListUCParams(val limit: Int, val offset: Int)

