package com.example.baseproject.presentation.pokemoninformation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baseproject.presentation.common.*
import com.example.baseproject.presentation.common.scene.SceneViewModel
import com.example.domain.model.PokemonInformation
import com.example.domain.usecase.*
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PokemonInformationViewModel @Inject constructor(
    private val getPokemonInformationUC: GetPokemonInformationUC,
    private val catchPokemonUC: CatchPokemonUC,
    private val releasePokemonUC: ReleasePokemonUC
) : SceneViewModel() {
    private val pokemonInformationLiveData = MutableLiveData<StateEvent<PokemonInformation>>()
    fun pokemonInformationLiveData(): LiveData<StateEvent<PokemonInformation>> = pokemonInformationLiveData

    private val catchPokemonLiveData = MutableLiveData<StateEvent<Unit>>()
    fun catchPokemonLiveData(): LiveData<StateEvent<Unit>> = catchPokemonLiveData

    init {
        baseEventsLiveData.postValue(ViewModelLoading<Unit>())
    }

    fun getPokemonInformation(pokemonName: String) {
        getPokemonInformationUC.getSingle(GetPokemonInformationParamsUC(pokemonName))
            .doOnSuccess { pokemonInformation ->
                pokemonInformationLiveData.postValue(ViewModelSuccess<PokemonInformation>(pokemonInformation))
            }.doOnError {
                pokemonInformationLiveData.postValue(ViewModelError(it))
            }.doFinally {
                baseEventsLiveData.postValue(ViewModelDismissLoading<Unit>())
            }.ignoreElement().onErrorComplete().subscribe().addTo(disposables)
    }

    fun catchPokemon(pokemonName: String) {
        catchPokemonUC.getCompletable(CatchPokemonParamsUC(pokemonName = pokemonName))
            .doOnComplete {
                catchPokemonLiveData.postValue(ViewModelSuccess(Unit))
            }.subscribe()
            .addTo(disposables)
    }

    fun releasePokemon(pokemonName: String) {
        releasePokemonUC.getCompletable(ReleasePokemonUCParams(pokemonName = pokemonName))
            .doOnComplete {
                catchPokemonLiveData.postValue(ViewModelSuccess(Unit))
            }.subscribe()
            .addTo(disposables)
    }
}