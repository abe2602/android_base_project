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
    private val pokemonListMutableLiveData = MutableLiveData<StateEvent<PokemonList>>()
    val pokemonListLiveData: LiveData<StateEvent<PokemonList>> = pokemonListMutableLiveData

    private val newPageLoadingMutableLiveData: MutableLiveData<StateEvent<*>> = MutableLiveData()
    val newPageLoadingLiveData: LiveData<StateEvent<*>> = newPageLoadingMutableLiveData

    private var limit: Int = 30
    private var offset: Int = 0
    private var totalFetchedItems = 0

    init {
        getFirstPokemonListPage()
    }

    fun getFirstPokemonListPage() {
        getPokemonListUC.getSingle(GetPokemonListUCParams(limit, offset))
            .doOnSuccess {
                baseEventsMutableLiveData.postValue(ViewModelLoading<Unit>())
                pokemonListMutableLiveData.postValue(ViewModelSuccess(it))
            }.doOnError {
                pokemonListMutableLiveData.postValue(ViewModelError(it))
            }.doFinally {
                baseEventsMutableLiveData.postValue(ViewModelDismissLoading<Unit>())
            }.ignoreElement().onErrorComplete().subscribe().addTo(disposables)
    }

    fun getPokemonListPage() {
        getPokemonListUC.getSingle(GetPokemonListUCParams(limit, offset))
            .doOnSuccess {
                newPageLoadingMutableLiveData.postValue(ViewModelLoading<Unit>())
                totalFetchedItems = it.pokemonList.size
                pokemonListMutableLiveData.postValue(ViewModelSuccess(it))
                offset = limit
                limit += 30
            }.doOnError {
                newPageLoadingMutableLiveData.postValue(ViewModelError<Unit>(it))
            }.doFinally {
                newPageLoadingMutableLiveData.postValue(ViewModelDismissLoading<Unit>())
            }.ignoreElement().onErrorComplete().subscribe().addTo(disposables)
    }

    fun navigateToPokemonDetails(pokemonName: String) {
        router.navigateTo(PokemonInformationScreen(pokemonName))
    }

}