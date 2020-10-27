package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetCaughtPokemonListUC @Inject constructor(
    @BackgroundScheduler backgroundScheduler: Scheduler,
    @MainScheduler mainScheduler: Scheduler,
    private val pokemonRepository: PokemonDataRepository
) : SingleUseCase<Unit, List<String>>(
    observeOn = mainScheduler,
    subscribeOn = backgroundScheduler
) {
    override fun getRawSingle(params: Unit): Single<List<String>> =
        pokemonRepository.getCaughtPokemonList()
}
