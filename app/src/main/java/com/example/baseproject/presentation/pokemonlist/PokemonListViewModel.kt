package com.example.baseproject.presentation.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baseproject.presentation.common.*
import com.example.baseproject.presentation.common.scene.SceneViewModel
import com.example.domain.model.PokemonList
import com.example.domain.usecase.GetPokemonListUC
import com.example.domain.usecase.GetPokemonListUCParams
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class PokemonListViewModel @Inject constructor(private var getPokemonListUC: GetPokemonListUC) :
    SceneViewModel() {
    private val pokemonListLiveData = MutableLiveData<StateEvent<PokemonList>>()
    fun getPokemonListLiveData(): LiveData<StateEvent<PokemonList>> = pokemonListLiveData

    private val newPageLoadingLiveData: MutableLiveData<StateEvent<*>> = MutableLiveData()
    fun getNewPageLoadingLiveData(): LiveData<StateEvent<*>> = newPageLoadingLiveData

    private var limit: Int = 30
    private var offset: Int = 0
    private var totalFetchedItems = 0

    init {
        getFirstPokemonListPage()
    }

    fun getFirstPokemonListPage() {
        getPokemonListUC.getSingle(GetPokemonListUCParams(limit, offset))
            .doOnSuccess {
                baseEventsLiveData.postValue(ViewModelLoading<Unit>())
                pokemonListLiveData.postValue(ViewModelSuccess(it))
            }.doOnError {
                pokemonListLiveData.postValue(ViewModelError(it))
            }.doFinally {
                baseEventsLiveData.postValue(ViewModelDismissLoading<Unit>())
            }.ignoreElement().onErrorComplete().subscribe().addTo(disposables)
    }

    fun getPokemonListPage() {
        getPokemonListUC.getSingle(GetPokemonListUCParams(limit, offset))
            .doOnSuccess {
                newPageLoadingLiveData.postValue(ViewModelLoading<Unit>())
                totalFetchedItems = it.pokemonList.size
                pokemonListLiveData.postValue(ViewModelSuccess(it))
                offset = limit
                limit += 30
            }.doOnError {
                newPageLoadingLiveData.postValue(ViewModelError<Unit>(it))
            }.doFinally {
                newPageLoadingLiveData.postValue(ViewModelDismissLoading<Unit>())
            }.ignoreElement().onErrorComplete().subscribe().addTo(disposables)
    }

    fun navigateToPokemonDetails(pokemonName: String) {
        router.navigateTo(PokemonInformationScreen(pokemonName))
    }

}