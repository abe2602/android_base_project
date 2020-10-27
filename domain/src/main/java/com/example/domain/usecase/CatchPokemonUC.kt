package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class CatchPokemonUC @Inject constructor(
    @BackgroundScheduler backgroundScheduler: Scheduler,
    @MainScheduler mainScheduler: Scheduler,
    private val pokemonRepository: PokemonDataRepository
) : CompletableUseCase<CatchPokemonParamsUC>(
    observeOn = mainScheduler,
    subscribeOn = backgroundScheduler
) {
    override fun getRawCompletable(params: CatchPokemonParamsUC): Completable =
        pokemonRepository.catchPokemon(params.pokemonName)
}

data class CatchPokemonParamsUC(val pokemonName: String)