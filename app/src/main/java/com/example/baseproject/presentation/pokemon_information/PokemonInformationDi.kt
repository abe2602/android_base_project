package com.example.baseproject.presentation.pokemon_information

import com.example.baseproject.common.PerScreen
import com.example.baseproject.presentation.common.ApplicationComponent
import com.example.baseproject.presentation.common.FlowContainerComponent
import dagger.Component
import dagger.Module
import dagger.Provides


@Module
class PokemonInformationModule(private val pokemonInformationUI: PokemonInformationUi) {
    @Provides
    fun getPokemonInformationView(): PokemonInformationUi = pokemonInformationUI
}

@PerScreen
@Component(
    modules = [PokemonInformationModule::class],
    dependencies = [FlowContainerComponent::class, ApplicationComponent::class]
)
interface PokemonInformationComponent {
    fun inject(pokemonInformationView: PokemonInformationView)
}