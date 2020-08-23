package com.example.baseproject.screentwo

import com.example.baseproject.FlowContainerComponent
import com.example.baseproject.PerScreen
import dagger.Component

@PerScreen
@Component(dependencies = [FlowContainerComponent::class])
interface FragmentTwoComponent {
    fun inject(fragmentTwoFragment: FragmentTwo)
}