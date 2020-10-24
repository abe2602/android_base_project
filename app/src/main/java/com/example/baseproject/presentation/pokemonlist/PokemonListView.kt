package com.example.baseproject.presentation.pokemonlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.presentation.common.BackButtonListener
import com.example.baseproject.presentation.common.FlowContainerFragment
import com.example.baseproject.presentation.common.PokedexApplication
import com.example.domain.model.Pokemon
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.frament_pokemon_list.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PokemonListView : Fragment(), PokemonListUi, DisposableHolder by DisposableHolderDelegate(),
    BackButtonListener {

    companion object {
        fun newInstance(): PokemonListView = PokemonListView()
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var presenter: PokemonListPresenter

    override val onViewCreated: PublishSubject<Unit> = PublishSubject.create<Unit>()
    override val onViewLoaded: PublishSubject<Unit> = PublishSubject.create<Unit>()
    override val onPokemonClick: PublishSubject<Unit> = PublishSubject.create<Unit>()
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        onViewCreated.onNext(Unit)
    }

    override fun displayPokemonList(pokemonList: List<Pokemon>) {
        pokemonListAdapter.setData(pokemonList)
    }

    override fun onBackPressed(): Boolean {
        TODO("Not yet implemented")
    }

    private fun setupRecyclerView() {
        pokemonListAdapter = PokemonListAdapter()
        pokemonListRecyclerView.layoutManager = LinearLayoutManager(context)
        pokemonListRecyclerView.adapter = pokemonListAdapter
    }
}
