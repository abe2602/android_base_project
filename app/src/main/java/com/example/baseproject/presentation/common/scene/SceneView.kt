package com.example.baseproject.presentation.common.scene

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.baseproject.R
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.presentation.common.BackButtonListener
import com.example.baseproject.presentation.common.ViewModelLoading
import com.example.baseproject.presentation.common.clicks
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.loading_view.*

abstract class SceneView : Fragment(), SceneUi, BackButtonListener,
    DisposableHolder by DisposableHolderDelegate() {

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

    open fun observeLiveData() {
        viewModel.getBaseEventsLiveData().observe(viewLifecycleOwner, Observer {
            if (it is ViewModelLoading) {
                displayLoading()
            } else {
                dismissLoading()
            }
        })
    }

    override fun onBackPressed(): Boolean {
        viewModel.onBackPressed()
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

    protected fun displayBlockingError(
        contentView: View?,
        errorLayout: View?
    ) {
        contentView?.visibility = View.GONE
        errorLayout?.visibility = View.VISIBLE
    }

    protected fun dismissBlockingError(
        contentView: View?,
        errorLayout: View?
    ) {
        contentView?.visibility = View.VISIBLE
        errorLayout?.visibility = View.GONE
    }
}