package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class ReleasePokemonUC @Inject constructor(
    @BackgroundScheduler backgroundScheduler: Scheduler,
    @MainScheduler mainScheduler: Scheduler,
    private val pokemonRepository: PokemonDataRepository
) : CompletableUseCase<ReleasePokemonUCParams>(
    observeOn = mainScheduler,
    subscribeOn = backgroundScheduler
) {
    override fun getRawCompletable(params: ReleasePokemonUCParams): Completable =
        pokemonRepository.releasePokemon(params.pokemonName)
}

data class ReleasePokemonUCParams(val pokemonName: String)