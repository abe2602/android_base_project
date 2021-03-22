package com.example.baseproject.presentation.caughtpokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.common.CatchPokemonDataObservable
import com.example.baseproject.presentation.common.StateEvent
import com.example.baseproject.presentation.common.ViewModelDismissLoading
import com.example.baseproject.presentation.common.ViewModelLoading
import com.example.baseproject.presentation.common.ViewModelSuccess
import com.example.baseproject.presentation.common.scene.SceneViewModel
import com.example.domain.usecase.GetCaughtPokemonListUC
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class CaughtPokemonViewModel @Inject constructor(
    private val getCaughtPokemonListUC: GetCaughtPokemonListUC,
) : SceneViewModel() {

    private val caughtPokemonListLiveData = MutableLiveData<StateEvent<List<String>>>()
    fun caughtPokemonListLiveData(): LiveData<StateEvent<List<String>>> = caughtPokemonListLiveData

    init {
        baseEventsMutableLiveData.postValue(ViewModelLoading<Unit>())
        getCaughtPokemonList()
    }

    private fun getCaughtPokemonList() {
        viewModelScope.launch {
            getCaughtPokemonListUC.getFlow(Unit).onEach {
                caughtPokemonListLiveData.postValue(ViewModelSuccess(it))
            }.collect {
                baseEventsMutableLiveData.postValue(ViewModelDismissLoading<Unit>())
            }
        }
    }
}