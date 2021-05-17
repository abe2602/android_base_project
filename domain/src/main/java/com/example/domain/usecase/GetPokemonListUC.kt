package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.model.PokemonList
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetPokemonListUC @Inject constructor(
    @BackgroundScheduler backgroundScheduler: Scheduler,
    @MainScheduler mainScheduler: Scheduler,
    private val pokemonRepository: PokemonDataRepository
) : SingleUseCase<GetPokemonListUCParams, PokemonList>(
    observeOn = mainScheduler,
    subscribeOn = backgroundScheduler
) {
    override fun getRawSingle(params: GetPokemonListUCParams): Single<PokemonList> =
        pokemonRepository.getPokemonList(params.limit, params.offset)
}

data class GetPokemonListUCParams(val limit: Int, val offset: Int)

