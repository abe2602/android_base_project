package com.example.baseproject.presentation.pokemonlist

import android.os.Handler
import com.example.baseproject.R
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.presentation.common.clicks
import com.example.domain.model.Pokemon
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_no_connection_error.view.*
import kotlinx.android.synthetic.main.my_item_layout.view.*

class PokemonListAdapter : GroupAdapter<GroupieViewHolder>() {
    private val onChoosePokemonSubject: PublishSubject<String> = PublishSubject.create()
    val onChoosePokemon: Observable<String> get() = onChoosePokemonSubject

    private val onRequestMoreItemsSubject: PublishSubject<Unit> = PublishSubject.create()
    val onRequestMoreItems: Observable<Unit> get() = onRequestMoreItemsSubject

    private val onTryAgainSubject: PublishSubject<Unit> = PublishSubject.create()
    val onTryAgain: Observable<Unit> get() = onTryAgainSubject

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
            //receivedPokemonList.addAll(pokemonList)

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
            viewHolder.itemView.retryButton.clicks().subscribe(onTryAgainSubject)
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
                pokemonListLayout.clicks().doOnNext {
                    onChoosePokemonSubject.onNext(pokemon.name)
                }.subscribe().addTo(disposables)

                pokemonName.text = pokemon.name

                if (totalFetchedItems - 3 == position && hasMore) {
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

