package com.example.baseproject.presentation.caughtpokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.baseproject.common.CatchPokemonDataObservable
import com.example.baseproject.presentation.common.StateEvent
import com.example.baseproject.presentation.common.ViewModelDismissLoading
import com.example.baseproject.presentation.common.ViewModelLoading
import com.example.baseproject.presentation.common.ViewModelSuccess
import com.example.baseproject.presentation.common.scene.SceneViewModel
import com.example.domain.usecase.GetCaughtPokemonListUC
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class CaughtPokemonViewModel @Inject constructor(
    private val getCaughtPokemonListUC: GetCaughtPokemonListUC,
    @CatchPokemonDataObservable private val catchPokemonDataObservable: Observable<Unit>
) : SceneViewModel() {
    private val caughtPokemonListMutableLiveData = MutableLiveData<StateEvent<List<String>>>()
    val caughtPokemonListLiveData: LiveData<StateEvent<List<String>>> = caughtPokemonListMutableLiveData

    init {
        baseEventsMutableLiveData.postValue(ViewModelLoading<Unit>())
        getCaughtPokemonList()
    }

    private fun getCaughtPokemonList() {
        Observable.merge(catchPokemonDataObservable, Observable.just(Unit)).flatMapSingle {
            getCaughtPokemonListUC.getSingle(Unit).doOnSuccess {
                caughtPokemonListMutableLiveData.postValue(ViewModelSuccess(it))
            }.doFinally {
                baseEventsMutableLiveData.postValue(ViewModelDismissLoading<Unit>())
            }
        }.subscribe().addTo(disposables)
    }
}