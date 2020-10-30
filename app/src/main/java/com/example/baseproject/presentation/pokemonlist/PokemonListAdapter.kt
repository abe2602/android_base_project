package com.example.baseproject.presentation.pokemonlist

import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.R
import com.example.baseproject.presentation.common.clicks
import com.example.domain.model.Pokemon
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.my_item_layout.view.*

class PokemonListAdapter : GroupAdapter<GroupieViewHolder>() {
    private val onChoosePokemonSubject: PublishSubject<String> = PublishSubject.create()
    val onChoosePokemon: Observable<String> get() = onChoosePokemonSubject

    private val onRequestMoreItemsSubject: PublishSubject<Unit> = PublishSubject.create()
    val onRequestMoreItems: Observable<Unit> get() = onRequestMoreItemsSubject
    private var totalItems: Int = 0

    fun setData(pokemonList: List<Pokemon>, totalFetchedItems: Int) {
        totalItems = totalFetchedItems
        pokemonList.forEach {
            add(PokemonItem(pokemon = it, hasMore = totalItems <= 151))
        }
    }

    private inner class ErrorItem : Item<GroupieViewHolder>(){
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        }

        override fun getLayout(): Int = R.layout.item_no_connection_error
    }

    private inner class LoadingItem : Item<GroupieViewHolder>(){
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        }

        override fun getLayout(): Int = R.layout.item_progress_indicator
    }

    private inner class PokemonItem(private val pokemon: Pokemon, private val hasMore: Boolean) : Item<GroupieViewHolder>(), DisposableHolder by DisposableHolderDelegate() {

        override fun getLayout(): Int = R.layout.my_item_layout

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            with(viewHolder.itemView) {
                pokemonListLayout.clicks().doOnNext {
                    onChoosePokemonSubject.onNext(pokemon.name)
                }.subscribe().addTo(disposables)

                pokemonName.text = pokemon.name

                if(totalItems - 3 == position && hasMore) {
                    onRequestMoreItemsSubject.onNext(Unit)
                }
            }

        }

        override fun unbind(viewHolder: GroupieViewHolder) {
            super.unbind(viewHolder)
            disposeAll()
        }
    }
}

