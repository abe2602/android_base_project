package com.example.baseproject.presentation.pokemonlist

import androidx.fragment.app.FragmentActivity
import com.example.baseproject.presentation.common.FlowContainerComponent
import com.example.baseproject.common.PerScreen
import com.example.baseproject.presentation.common.ApplicationComponent
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class PokemonListModule(private val pokemonListUI: PokemonListUi) {
    @Provides
    fun getPokemonListView(): PokemonListUi = pokemonListUI
}

@PerScreen
@Component(
    modules = [PokemonListModule::class],
    dependencies = [FlowContainerComponent::class, ApplicationComponent::class]
)
interface PokemonListComponent {
    fun inject(pokemonListView: PokemonListView)
}