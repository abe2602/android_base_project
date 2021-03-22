package com.example.baseproject.presentation.pokemonlist

import android.os.Handler
import com.example.baseproject.R
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.domain.model.Pokemon
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.item_no_connection_error.view.*
import kotlinx.android.synthetic.main.my_item_layout.view.*

class PokemonListAdapter(
    private val onChoosePokemonClickListener: (String) -> Unit,
    private val onRequestMoreItemsListener: () -> Unit,
    private val onTryAgainListener: () -> Unit
) : GroupAdapter<GroupieViewHolder>() {
    private var totalFetchedItems: Int = 0
    private var hasMore: Boolean = true

    private val newPageStatusSection = Section()
    private var itemsSection = Section()
    private val loadingItem = LoadingItem()
    private val errorItem = ErrorItem()

    fun setData(pokemonList: List<Pokemon>, totalFetchedItems: Int, totalItems: Int) {
        Handler().post {
            clear()

            this.totalFetchedItems = totalFetchedItems
            hasMore = totalFetchedItems <= totalItems

            itemsSection = Section().apply {
                pokemonList.distinct().forEach {
                    add(PokemonItem(pokemon = it, hasMore = hasMore))
                }
            }

            add(itemsSection)
            add(newPageStatusSection)
        }

        if (!hasMore) {
            removeNewPageLoading()
        }
    }

    fun addNewPageError() {
        Handler().post {
            newPageStatusSection.clear()
            newPageStatusSection.add(errorItem)
        }
    }

    fun addNewPageLoading() {
        Handler().post {
            newPageStatusSection.clear()
            newPageStatusSection.add(loadingItem)
        }
    }

    fun removeNewPageLoading() {
        newPageStatusSection.remove(loadingItem)
    }

    private inner class ErrorItem : Item<GroupieViewHolder>(),
        DisposableHolder by DisposableHolderDelegate() {
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.retryButton.setOnClickListener {
                onTryAgainListener.invoke()
            }
        }

        override fun getLayout(): Int = R.layout.item_no_connection_error

        override fun unbind(viewHolder: GroupieViewHolder) {
            super.unbind(viewHolder)
            disposeAll()
        }
    }

    private inner class LoadingItem : Item<GroupieViewHolder>() {
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        }

        override fun getLayout(): Int = R.layout.item_progress_indicator
    }

    private inner class PokemonItem(private val pokemon: Pokemon, private val hasMore: Boolean) :
        Item<GroupieViewHolder>(), DisposableHolder by DisposableHolderDelegate() {

        override fun getLayout(): Int = R.layout.my_item_layout

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            with(viewHolder.itemView) {
                pokemonListLayout.setOnClickListener {
                    onChoosePokemonClickListener.invoke(pokemon.name)
                }

                pokemonName.text = pokemon.name

                if (totalFetchedItems - 3 == position && hasMore) {
                    onRequestMoreItemsListener.invoke()
                }
            }

        }

        override fun unbind(viewHolder: GroupieViewHolder) {
            super.unbind(viewHolder)
            disposeAll()
        }
    }
}

