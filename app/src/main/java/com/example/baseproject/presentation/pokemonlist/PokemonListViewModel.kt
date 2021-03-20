package com.example.baseproject.presentation.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.presentation.common.*
import com.example.baseproject.presentation.common.scene.SceneViewModel
import com.example.domain.model.PokemonList
import com.example.domain.usecase.GetPokemonListUC
import com.example.domain.usecase.GetPokemonListUCParams
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
        viewModelScope.launch{
            getPokemonListUC
                .getFlow(GetPokemonListUCParams(limit, offset))
                .onEach {
                    baseEventsMutableLiveData.postValue(ViewModelLoading<Unit>())
                    pokemonListMutableLiveData.postValue(ViewModelSuccess(it))
                }.catch {
                    pokemonListMutableLiveData.postValue(ViewModelError(it))
                }
                .collect {
                    baseEventsMutableLiveData.postValue(ViewModelDismissLoading<Unit>())
                }
        }
    }

    fun getPokemonListPage() {
        viewModelScope.launch{
            getPokemonListUC
                .getFlow(GetPokemonListUCParams(limit, offset))
                .onEach {
                    newPageLoadingMutableLiveData.postValue(ViewModelLoading<Unit>())
                    totalFetchedItems = it.pokemonList.size
                    pokemonListMutableLiveData.postValue(ViewModelSuccess(it))
                    offset = limit
                    limit += 30
                }.catch {
                    newPageLoadingMutableLiveData.postValue(ViewModelError<Unit>(it))
                }
                .collect {
                    newPageLoadingMutableLiveData.postValue(ViewModelDismissLoading<Unit>())
                }
        }
    }

    fun navigateToPokemonDetails(pokemonName: String) {
        router.navigateTo(PokemonInformationScreen(pokemonName))
    }

}