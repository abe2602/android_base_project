package com.example.baseproject.presentation.common

import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.example.baseproject.presentation.pokemonlist.PokemonListView
import com.example.baseproject.presentation.screentwo.FragmentTwo
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
class FragmentTwoScreen : Screen() {
    override fun getFragment(): Fragment {
        return FragmentTwo.newInstance()
    }
}