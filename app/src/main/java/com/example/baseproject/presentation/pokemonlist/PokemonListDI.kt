package com.example.baseproject.presentation.pokemonlist

import androidx.fragment.app.FragmentActivity
import com.example.baseproject.presentation.common.FlowContainerComponent
import com.example.baseproject.common.PerScreen
import com.example.baseproject.presentation.common.ApplicationComponent
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class PokemonListModule(private val fragmentActivity: FragmentActivity) {
    @Provides
    fun getPokemonListView(activity: FragmentActivity): PokemonListUi = PokemonListView(activity)

    @Provides
    fun getFragmentActivity(): FragmentActivity = fragmentActivity
}

@PerScreen
@Component(modules = [PokemonListModule::class], dependencies = [FlowContainerComponent::class, ApplicationComponent::class])
interface PokemonListComponent {
    fun inject(pokemonListPresenter: PokemonListPresenter)
}