package com.example.baseproject.presentation.pokemoninformation

import androidx.lifecycle.ViewModelProvider
import com.example.baseproject.common.PerScreen
import com.example.baseproject.presentation.common.ApplicationComponent
import com.example.baseproject.presentation.common.FlowContainerComponent
import com.example.baseproject.presentation.common.ViewModelModule
import dagger.Component
import dagger.Module

@Module(includes = [ViewModelModule::class])
class PokemonInformationModule

@PerScreen
@Component(
    modules = [PokemonInformationModule::class],
    dependencies = [FlowContainerComponent::class, ApplicationComponent::class]
)
interface PokemonInformationComponent {
    fun provideViewModelFactory(): ViewModelProvider.Factory
    fun inject(pokemonInformationView: PokemonInformationView)
}