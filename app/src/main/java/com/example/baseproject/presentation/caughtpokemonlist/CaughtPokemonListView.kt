package com.example.baseproject.presentation.caughtpokemonlist

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
import com.example.baseproject.presentation.common.scene.SceneView
import kotlinx.android.synthetic.main.fragment_caught_pokemon_list_view.*
import kotlinx.android.synthetic.main.toolbar_view.*
import javax.inject.Inject

class CaughtPokemonListView : SceneView(), CaughtPokemonListUi {
    companion object {
        fun newInstance() = CaughtPokemonListView()
    }

    @Inject
    lateinit var presenter: CaughtPokemonListPresenter

    private val component: CaughtPokemonListComponent? by lazy {
        DaggerCaughtPokemonListComponent
            .builder()
            .caughtPokemonListModule(CaughtPokemonListModule(this))
            .applicationComponent((activity?.application as? MainApplication)?.applicationComponent)
            .flowContainerComponent((parentFragment as? FlowContainerFragment)?.component)
            .build()
    }

    private lateinit var caughtPokemonListAdapter: CaughtPokemonListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component?.inject(this)
        caughtPokemonListAdapter = CaughtPokemonListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caught_pokemon_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitleText.text = getString(R.string.pokemon_list_title)
        setupAppBar(toolbar as Toolbar, false)
        setupRecyclerView()
    }


    override fun displayCaughtPokemonList(caughtPokemonList: List<String>) {
        caughtPokemonListAdapter.setData(caughtPokemonList)
    }

    private fun setupRecyclerView() {
        caughtPokemonListRecyclerView.layoutManager = LinearLayoutManager(context)
        caughtPokemonListRecyclerView.adapter = caughtPokemonListAdapter
    }

}