package com.example.baseproject.presentation.common

import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.example.baseproject.presentation.caughtpokemonlist.CaughtPokemonListView
import com.example.baseproject.presentation.pokemoninformation.PokemonInformationView
import com.example.baseproject.presentation.pokemonlist.PokemonListView
import kotlinx.android.parcel.Parcelize
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class Screen : SupportAppScreen(), Parcelable

@Parcelize
class PokemonListScreen : Screen() {
    override fun getFragment(): Fragment {
        return PokemonListView.newInstance()
    }
}

@Parcelize
class CaughtPokemonListScreen : Screen() {
    override fun getFragment(): Fragment {
        return CaughtPokemonListView.newInstance()
    }
}

@Parcelize
class PokemonInformationScreen(private val pokemonName: String) : Screen() {
    override fun getFragment(): Fragment {
        return PokemonInformationView.newInstance(pokemonName)
    }
}