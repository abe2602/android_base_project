package com.example.baseproject.presentation.pokemonlist

import androidx.lifecycle.ViewModelProvider
import com.example.baseproject.common.PerScreen
import com.example.baseproject.presentation.common.ApplicationComponent
import com.example.baseproject.presentation.common.FlowContainerComponent
import com.example.baseproject.presentation.common.ViewModelModule
import dagger.Component
import dagger.Module

@Module(includes = [ViewModelModule::class])
class PokemonListModule

@PerScreen
@Component(
    modules = [PokemonListModule::class],
    dependencies = [FlowContainerComponent::class, ApplicationComponent::class]
)
interface PokemonListComponent {
    fun provideViewModelFactory(): ViewModelProvider.Factory
    fun inject(pokemonListView: PokemonListView)
}