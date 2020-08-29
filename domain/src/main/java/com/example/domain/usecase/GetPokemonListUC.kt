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
) : SingleUseCase<Unit, List<Pokemon>>(
    observeOn = mainScheduler,
    subscribeOn = backgroundScheduler
) {
    override fun getRawSingle(params: Unit): Single<List<Pokemon>> =
        pokemonRepository.getPokemonList()
}

