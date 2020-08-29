package com.example.baseproject.presentation.pokemonlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseproject.R
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.presentation.common.BackButtonListener
import com.example.baseproject.presentation.common.FlowContainerFragment
import com.example.baseproject.presentation.common.FragmentTwoScreen
import com.example.baseproject.presentation.common.PokedexApplication
import com.example.domain.usecase.GetPokemonListUC
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.frament_one.*
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FragmentOne : Fragment(), DisposableHolder by DisposableHolderDelegate(), BackButtonListener {
    private lateinit var numbersAdapter: FragmentOneAdapter

    @Inject
    lateinit var cicerone: Cicerone<Router>

    @Inject
    lateinit var getPokemonListUC: GetPokemonListUC

    private val component: FragmentOneComponent? by lazy {
        DaggerFragmentOneComponent
            .builder()
            .applicationComponent((activity?.application as? PokedexApplication)?.applicationComponent)
            .flowContainerComponent((parentFragment as? FlowContainerFragment)?.component)
            .build()
    }

    companion object {
        fun newInstance(): FragmentOne = FragmentOne()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.frament_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component?.inject(this)

        numbersAdapter = FragmentOneAdapter()
        numberListRecyclerView.layoutManager = GridLayoutManager(context, 1)
        numberListRecyclerView.adapter = numbersAdapter

        if(savedInstanceState == null) {
            getPokemonListUC.getSingle(Unit)
                .doOnSuccess {
                    numbersAdapter.setData(it)
                }.doOnError {
                    Log.d("HelpMe", it.toString())
                }.subscribe().addTo(disposables)
        }

        numbersAdapter.onClick.doOnNext {
            cicerone.router.navigateTo(FragmentTwoScreen())
        }.subscribe().addTo(disposables)
    }

    override fun onBackPressed(): Boolean {
        cicerone.router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeAll()
    }
}