package com.example.baseproject.presentation.pokemonlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.presentation.common.BackButtonListener
import com.example.baseproject.presentation.common.FlowContainerFragment
import com.example.baseproject.presentation.common.FragmentTwoScreen
import com.example.baseproject.presentation.common.PokedexApplication
import com.example.domain.model.Pokemon
import com.example.domain.usecase.GetPokemonListUC
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import ru.terrakok.cicerone.Router
import java.io.Serializable
import javax.inject.Inject

class PokemonListPresenter : Fragment(), DisposableHolder by DisposableHolderDelegate(),
    BackButtonListener {

    companion object {
        fun newInstance(): PokemonListPresenter = PokemonListPresenter()
    }

    @Inject
    lateinit var ui: PokemonListUi

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var getPokemonListUC: GetPokemonListUC

    fun <T> List<T>.toArrayList() = ArrayList<T>(this)
    private var pokemonListState: StateEventLiveData<ArrayList<Pokemon>> = StateEventLiveData()

    private val onViewLoaded: PublishSubject<Unit> = PublishSubject.create<Unit>()
    private val onViewResumed: PublishSubject<Unit> = PublishSubject.create<Unit>()

    private val component: PokemonListComponent? by lazy {
        DaggerPokemonListComponent
            .builder()
            .pokemonListModule(activity?.let { fragmentActivity ->
                PokemonListModule(
                    fragmentActivity
                )
            })
            .applicationComponent((activity?.application as? PokedexApplication)?.applicationComponent)
            .flowContainerComponent((parentFragment as? FlowContainerFragment)?.component)
            .build()
    }

    init {
        onViewResumed.take(1).subscribe(onViewLoaded)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ui.buildContainerView(container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component?.inject(this)
        observeView()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui.loadComponents()
        observeEvents(lifecycle)
    }

    private fun observeEvents(lifecycle: Lifecycle) {
        pokemonListState.observeStateEvent(lifecycle, Observer {
            ui.displayPokemonList(it)
        })
    }

    private fun observeView() {
        onViewLoaded.flatMapCompletable {
            getPokemonListUC.getSingle(Unit)
                .doOnSuccess {
                    //ui.displayPokemonList(it)
                    pokemonListState.postEvent(it.toArrayList())
                }.doOnError {
                    Log.d("HelpMe", it.toString())
                }.ignoreElement().onErrorComplete()
        }.subscribe().addTo(disposables)

        ui.onPokemonClick.doOnNext {
            router.navigateTo(FragmentTwoScreen())
        }.subscribe().addTo(disposables)

    }

    override fun onResume() {
        super.onResume()
        onViewResumed.onNext(Unit)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeAll()
    }
}

class StateEventLiveData<T : Serializable> : MutableLiveData<T>() {
    fun postEvent(data: T) {
        super.postValue(data)
    }

    fun observeStateEvent(owner: Lifecycle, observer: Observer<in T>) {
        this.observe({ owner }, { stateEvent ->
            if (stateEvent == null) {
                return@observe
            }

            observer.onChanged(stateEvent)
        })
    }
}