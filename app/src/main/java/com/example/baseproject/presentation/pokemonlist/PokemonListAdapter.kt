package com.example.baseproject.presentation.pokemonlist

import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.R
import com.example.baseproject.presentation.common.clicks
import com.example.domain.model.Pokemon
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.my_item_layout.view.*

class PokemonListAdapter : GroupAdapter<GroupieViewHolder>() {
    private val onChoosePokemonSubject: PublishSubject<String> = PublishSubject.create()
    val onChoosePokemon: Observable<String> get() = onChoosePokemonSubject

    fun setData(pokemonList: List<Pokemon>) {
        pokemonList.forEach {
            add(PokemonItem(pokemon = it))
        }
    }

    private inner class PokemonItem(private val pokemon: Pokemon) : Item<GroupieViewHolder>(), DisposableHolder by DisposableHolderDelegate() {
        override fun getLayout(): Int = R.layout.my_item_layout

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            with(viewHolder.itemView) {
                pokemonListLayout.clicks().doOnNext {
                    onChoosePokemonSubject.onNext(pokemon.name)
                }.subscribe().addTo(disposables)

                pokemonName.text = pokemon.name
            }
        }

        override fun unbind(viewHolder: GroupieViewHolder) {
            disposeAll()
            super.unbind(viewHolder)
        }
    }
}

