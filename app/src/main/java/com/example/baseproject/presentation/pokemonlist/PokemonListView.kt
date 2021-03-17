package com.example.baseproject.presentation.pokemonlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.R
import com.example.baseproject.presentation.common.*
import com.example.baseproject.presentation.common.scene.SceneView
import com.example.domain.model.Pokemon
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.frament_pokemon_list.*
import kotlinx.android.synthetic.main.toolbar_view.*
import kotlinx.android.synthetic.main.view_empty_state.*
import javax.inject.Inject

class PokemonListView : SceneView() {
    @Inject
    override lateinit var viewModel: PokemonListViewModel

    private lateinit var pokemonListAdapter: PokemonListAdapter

    private val receivedPokemonList: MutableList<Pokemon> = mutableListOf()

    companion object {
        fun newInstance(): PokemonListView = PokemonListView()
    }

    private val component: PokemonListComponent? by lazy {
        DaggerPokemonListComponent
            .builder()
            .pokemonListModule(PokemonListModule())
            .applicationComponent((activity?.application as? MainApplication)?.applicationComponent)
            .flowContainerComponent((parentFragment as? FlowContainerFragment)?.component)
            .build()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component?.inject(this)
        pokemonListAdapter = PokemonListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frament_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarTitleText.text = getString(R.string.pokemon_list_title)
        setupAppBar(toolbar as Toolbar, false)
        setupRecyclerView()
        observeLiveData()

        pokemonListAdapter.onRequestMoreItems.doOnNext {
            viewModel.getPokemonListPage()
        }.subscribe().addTo(disposables)

        pokemonListAdapter.onChoosePokemon.doOnNext {
            viewModel.navigateToPokemonDetails(it)
        }.subscribe().addTo(disposables)

        pokemonListAdapter.onTryAgain.doOnNext {
            viewModel.getNewPageLoadingLiveData()
        }.subscribe().addTo(disposables)

        tryAgainActionButton.clicks().doOnNext {
            viewModel.getFirstPokemonListPage()
        }.subscribe().addTo(disposables)
    }

    private fun setupRecyclerView() {
        pokemonListRecyclerView.layoutManager = LinearLayoutManager(context)
        pokemonListRecyclerView.adapter = pokemonListAdapter
    }

    override fun observeLiveData() {
        super.observeLiveData()

        with(viewModel) {
            getPokemonListLiveData().observe(viewLifecycleOwner, Observer { pokemonListState ->
                if (pokemonListState is ViewModelSuccess) {
                    val pokemonListData = pokemonListState.getData()
                    dismissBlockingError(pokemonListRecyclerView, errorLayout)
                    receivedPokemonList.addAll(pokemonListData.pokemonList)
                    pokemonListAdapter.setData(receivedPokemonList, pokemonListData.pokemonList.size, pokemonListData.total)
                } else {
                    displayBlockingError(pokemonListRecyclerView, errorLayout)
                }
            })

            getNewPageLoadingLiveData().observe(viewLifecycleOwner, Observer { pokemonListPaginationState ->
                if (pokemonListPaginationState is ViewModelLoading) {
                    pokemonListAdapter.addNewPageLoading()
                } else {
                    if(pokemonListPaginationState is ViewModelError) {
                        pokemonListAdapter.addNewPageError()
                    }
                    pokemonListAdapter.removeNewPageLoading()
                }
            })
        }
    }
}
