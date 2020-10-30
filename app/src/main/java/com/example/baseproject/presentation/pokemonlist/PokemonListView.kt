package com.example.baseproject.presentation.pokemonlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.presentation.common.FlowContainerFragment
import com.example.baseproject.presentation.common.MainApplication
import com.example.baseproject.presentation.common.PokemonInformationScreen
import com.example.baseproject.presentation.common.scene.SceneView
import com.example.domain.model.Pokemon
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.frament_pokemon_list.*
import kotlinx.android.synthetic.main.toolbar_view.*
import javax.inject.Inject

class PokemonListView : SceneView(), PokemonListUi {

    companion object {
        fun newInstance(): PokemonListView = PokemonListView()
    }

    override val onChoosePokemon: PublishSubject<String> = PublishSubject.create<String>()
    override val onRequestMorePokemon: PublishSubject<Unit> = PublishSubject.create<Unit>()

    @Inject
    lateinit var presenter: PokemonListPresenter

    private lateinit var pokemonListAdapter: PokemonListAdapter

    private val component: PokemonListComponent? by lazy {
        DaggerPokemonListComponent
            .builder()
            .pokemonListModule(PokemonListModule(this))
            .applicationComponent((activity?.application as? MainApplication)?.applicationComponent)
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
        pokemonListAdapter.onRequestMoreItems.subscribe(onRequestMorePokemon)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitleText.text = getString(R.string.pokemon_list_title)
        setupAppBar(toolbar as Toolbar, false)
        setupRecyclerView()

        onChoosePokemon.doOnNext { pokemonName ->
            router.navigateTo(PokemonInformationScreen(pokemonName))
        }.subscribe().addTo(disposables)
    }

    override fun displayNoInternetError() {
        pokemonListAdapter.addNewPageError()
    }

    override fun displayPokemonList(pokemonList: List<Pokemon>, totalFetchedItems: Int) {
        pokemonListAdapter.setData(pokemonList, totalFetchedItems)
    }

    private fun setupRecyclerView() {
        pokemonListRecyclerView.layoutManager = LinearLayoutManager(context)
        pokemonListRecyclerView.adapter = pokemonListAdapter
    }
}
