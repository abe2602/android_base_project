package com.example.baseproject.presentation.caughtpokemonlist

import androidx.lifecycle.ViewModelProvider
import com.example.baseproject.common.PerScreen
import com.example.baseproject.presentation.common.ApplicationComponent
import com.example.baseproject.presentation.common.FlowContainerComponent
import com.example.baseproject.presentation.common.ViewModelModule
import dagger.Component
import dagger.Module

@Module(includes = [ViewModelModule::class])
class CaughtPokemonListModule

@PerScreen
@Component(
    modules = [CaughtPokemonListModule::class],
    dependencies = [FlowContainerComponent::class, ApplicationComponent::class]
)
interface CaughtPokemonListComponent {
    fun provideViewModelFactory(): ViewModelProvider.Factory
    fun inject(caughtPokemonListView: CaughtPokemonListView)
}