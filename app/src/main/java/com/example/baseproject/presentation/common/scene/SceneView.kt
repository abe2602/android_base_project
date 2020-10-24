package com.example.baseproject.presentation.common.scene

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.presentation.common.BackButtonListener
import com.example.baseproject.presentation.common.FlowContainerFragment
import com.example.baseproject.presentation.common.MainApplication
import io.reactivex.subjects.PublishSubject
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class SceneView : Fragment(), SceneUi, BackButtonListener,
    DisposableHolder by DisposableHolderDelegate() {

    @Inject
    lateinit var router: Router

    override val onViewCreated: PublishSubject<Unit> = PublishSubject.create<Unit>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated.onNext(Unit)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposeAll()
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }
}