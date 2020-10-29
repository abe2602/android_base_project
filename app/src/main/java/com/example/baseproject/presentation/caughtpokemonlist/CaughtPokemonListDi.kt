package com.example.baseproject.presentation.caughtpokemonlist

import com.example.baseproject.common.PerScreen
import com.example.baseproject.presentation.common.ApplicationComponent
import com.example.baseproject.presentation.common.FlowContainerComponent
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class CaughtPokemonListModule(private val caughtPokemonListUi: CaughtPokemonListUi) {
    @Provides
    fun getCaughtPokemonListView(): CaughtPokemonListUi = caughtPokemonListUi
}

@PerScreen
@Component(
    modules = [CaughtPokemonListModule::class],
    dependencies = [FlowContainerComponent::class, ApplicationComponent::class]
)
interface CaughtPokemonListComponent {
    fun inject(caughtPokemonListView: CaughtPokemonListView)
}