package com.example.baseproject.screenone

import com.example.baseproject.FlowContainerComponent
import com.example.baseproject.PerScreen
import dagger.Component

@PerScreen
@Component(dependencies = [FlowContainerComponent::class])
interface FragmentOneComponent {
    fun inject(fragmentOneFragment: FragmentOne)
}