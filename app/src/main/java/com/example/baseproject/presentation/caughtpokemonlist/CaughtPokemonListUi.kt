package com.example.baseproject.presentation.caughtpokemonlist

import com.example.baseproject.presentation.common.scene.SceneUi

interface CaughtPokemonListUi : SceneUi {
    fun displayCaughtPokemonList(caughtPokemonList: List<String>)
}