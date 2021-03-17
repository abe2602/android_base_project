package com.example.baseproject.presentation.common.scene

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.presentation.common.StateEvent
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class SceneViewModel : ViewModel(), DisposableHolder by DisposableHolderDelegate() {
    @Inject
    lateinit var router: Router
    protected val baseEventsLiveData: MutableLiveData<StateEvent<*>> = MutableLiveData()

    fun getBaseEventsLiveData(): LiveData<StateEvent<*>> = baseEventsLiveData

    open fun onBackPressed() {
        router.exit()
    }
}