package com.example.baseproject.presentation.caughtpokemonlist

import com.example.baseproject.presentation.common.scene.SceneUi
import io.reactivex.Observable

interface CaughtPokemonListUi : SceneUi {
    fun displayCaughtPokemonList(caughtPokemonList: List<String>)
    fun displayBlockingError()
    fun displayNoCaughtPokemonError()

    val onTryAgain: Observable<Unit>
}