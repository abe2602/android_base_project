package com.example.baseproject.presentation.pokemonlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.presentation.common.*
import com.example.domain.model.Pokemon
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.frament_pokemon_list.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PokemonListView : Fragment(), PokemonListUi, DisposableHolder by DisposableHolderDelegate(),
    BackButtonListener {

    companion object {
        fun newInstance(): PokemonListView = PokemonListView()
    }

    override val onViewCreated: PublishSubject<Unit> = PublishSubject.create<Unit>()
    override val onChoosePokemon: PublishSubject<String> = PublishSubject.create<String>()

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var presenter: PokemonListPresenter

    private lateinit var pokemonListAdapter: PokemonListAdapter

    private val component: PokemonListComponent? by lazy {
        DaggerPokemonListComponent
            .builder()
            .pokemonListModule(PokemonListModule(this))
            .applicationComponent((activity?.application as? PokedexApplication)?.applicationComponent)
            .flowContainerComponent((parentFragment as? FlowContainerFragment)?.component)
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frament_pokemon_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component?.inject(this)
        pokemonListAdapter = PokemonListAdapter()
        pokemonListAdapter.onChoosePokemon.subscribe(onChoosePokemon)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        onViewCreated.onNext(Unit)

        onChoosePokemon.doOnNext {pokemonName ->
            router.navigateTo(PokemonInformationScreen(pokemonName))
        }.subscribe().addTo(disposables)
    }

    override fun displayPokemonList(pokemonList: List<Pokemon>) {
        pokemonListAdapter.setData(pokemonList)
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

    private fun setupRecyclerView() {
        pokemonListRecyclerView.layoutManager = LinearLayoutManager(context)
        pokemonListRecyclerView.adapter = pokemonListAdapter
    }

    override fun onDestroy() {
        disposeAll()
        super.onDestroy()
    }
}
