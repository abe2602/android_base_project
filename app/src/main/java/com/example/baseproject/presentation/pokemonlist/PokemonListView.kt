package com.example.baseproject.presentation.pokemonlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.Pokemon
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.frament_one.*
import javax.inject.Inject

class PokemonListView @Inject constructor(private val context: FragmentActivity) : PokemonListUi, LayoutContainer {
    private lateinit var pokemonListAdapter: PokemonListAdapter
    override lateinit var containerView: View

    override val onPokemonClick: PublishSubject<Unit> = PublishSubject.create()

    @LayoutRes
    val layoutResource: Int = com.example.baseproject.R.layout.frament_one

    override fun buildContainerView(parentViewGroup: ViewGroup?): View {
        containerView = LayoutInflater.from(context).inflate(layoutResource, parentViewGroup, false)
        clearFindViewByIdCache()
        return containerView
    }

    override fun displayPokemonList(pokemonList: List<Pokemon>) {
        pokemonListAdapter.setData(pokemonList)
    }

    override fun loadComponents() {
        pokemonListAdapter = PokemonListAdapter()
        numberListRecyclerView.layoutManager = GridLayoutManager(context, 1)
        numberListRecyclerView.adapter = pokemonListAdapter

        pokemonListAdapter.onClick.subscribe(onPokemonClick)
    }

}