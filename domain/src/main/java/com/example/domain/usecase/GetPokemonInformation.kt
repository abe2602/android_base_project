package com.example.domain.usecase

import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.model.PokemonInformation
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetPokemonInformationUC @Inject constructor(
    @BackgroundScheduler backgroundScheduler: Scheduler,
    @MainScheduler mainScheduler: Scheduler,
    private val pokemonRepository: PokemonDataRepository
) : SingleUseCase<GetPokemonInformationParamsUC, PokemonInformation>(
    observeOn = mainScheduler,
    subscribeOn = backgroundScheduler
) {
    override fun getRawSingle(params: GetPokemonInformationParamsUC): Single<PokemonInformation> =
        pokemonRepository.getPokemonInformation(params.pokemonName)
}

data class GetPokemonInformationParamsUC(val pokemonName: String)
