package com.example.baseproject.presentation.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.baseproject.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val pokemonListTag: String = "fragmentOneTag"
        private const val caughtPokemonListTag: String = "fragmentTwoTag"
    }

    private var activeFragmentTag: String? = pokemonListTag

    private val pokemonListFlowContainer: PokemonListFlowContainer by lazy {
        supportFragmentManager.findFragmentByTag(pokemonListTag) as? PokemonListFlowContainer
            ?: PokemonListFlowContainer.newInstance()
    }

    private val caughtPokemonListFlowContainer: CaughtPokemonListFlowContainer by lazy {
        supportFragmentManager.findFragmentByTag(caughtPokemonListTag) as? CaughtPokemonListFlowContainer
            ?: CaughtPokemonListFlowContainer.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavActions()

        supportFragmentManager.beginTransaction().hide(caughtPokemonListFlowContainer).show(pokemonListFlowContainer).commit()
        activeFragmentTag = pokemonListFlowContainer.tag
    }


    override fun onBackPressed() {
        val currentFragmentMoviesFlow = (supportFragmentManager.findFragmentByTag(activeFragmentTag) as? BackButtonListener)
        currentFragmentMoviesFlow?.let {
            if (!it.onBackPressed())
                finish()
        }
    }

    private fun setupNavActions(){
        supportFragmentManager.beginTransaction()
            .add(R.id.mainFlowContainer, pokemonListFlowContainer, pokemonListTag)
            .add(R.id.mainFlowContainer, caughtPokemonListFlowContainer, caughtPokemonListTag)
            .commitNow()

        //Cliques na bottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener { onNavigationItemSelected(it) }
    }

    //Navegação na bottomNavigation, retorna true se o item foi selecionado, false cc
    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().hide(caughtPokemonListFlowContainer).show(pokemonListFlowContainer).commit()
                activeFragmentTag = pokemonListFlowContainer.tag
                true
            }
            R.id.navigation_dashboard -> {
                supportFragmentManager.beginTransaction().hide(pokemonListFlowContainer).show(caughtPokemonListFlowContainer).commit()
                activeFragmentTag = caughtPokemonListFlowContainer.tag
                true
            }
            else -> {
                false
            }
        }
    }
}