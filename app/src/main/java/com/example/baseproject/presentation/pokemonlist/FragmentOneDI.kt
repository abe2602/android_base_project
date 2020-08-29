package com.example.baseproject.presentation.pokemonlist

import com.example.baseproject.presentation.common.FlowContainerComponent
import com.example.baseproject.common.PerScreen
import com.example.baseproject.presentation.common.ApplicationComponent
import dagger.Component

@PerScreen
@Component(dependencies = [FlowContainerComponent::class, ApplicationComponent::class])
interface FragmentOneComponent {
    fun inject(fragmentOneFragment: FragmentOne)
}