package com.example.baseproject.presentation.screentwo

import com.example.baseproject.presentation.common.FlowContainerComponent
import com.example.baseproject.common.PerScreen
import dagger.Component

@PerScreen
@Component(dependencies = [FlowContainerComponent::class])
interface FragmentTwoComponent {
    fun inject(fragmentTwoFragment: FragmentTwo)
}