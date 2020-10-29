package com.example.baseproject.presentation.caughtpokemonlist

import com.example.baseproject.R
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.my_item_layout.view.*

class CaughtPokemonListAdapter : GroupAdapter<GroupieViewHolder>() {

    fun setData(pokemonList: List<String>) {
        clear()
        pokemonList.forEach {
            add(PokemonItem(name = it))
        }
    }

    private inner class PokemonItem(private val name: String) : Item<GroupieViewHolder>(), DisposableHolder by DisposableHolderDelegate() {
        override fun getLayout(): Int = R.layout.my_item_layout

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            with(viewHolder.itemView) {
                pokemonName.text = name
            }
        }

        override fun unbind(viewHolder: GroupieViewHolder) {
            super.unbind(viewHolder)
            disposeAll()
        }
    }
}