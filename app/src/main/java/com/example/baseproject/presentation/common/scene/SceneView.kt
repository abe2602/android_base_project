package com.example.baseproject.presentation.common.scene

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.baseproject.R
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.presentation.common.BackButtonListener
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.loading_view.*
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

    override fun displayLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            loading.visibility = View.GONE
        }, 300)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposeAll()
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    protected fun setupAppBar(
        toolbar: Toolbar,
        showIndicator: Boolean = true,
        isModal: Boolean = false
    ) {
        if (showIndicator) {
            if (isModal) {
                toolbar.setNavigationIcon(R.drawable.ic_close)
            } else {
                toolbar.setNavigationIcon(R.drawable.ic_back)
            }

            toolbar.clicks().doOnNext {
                onBackPressed()
            }.subscribe().addTo(disposables)
        }
    }
}