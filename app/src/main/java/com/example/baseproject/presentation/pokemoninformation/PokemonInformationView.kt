package com.example.baseproject.presentation.pokemoninformation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.baseproject.R
import com.example.baseproject.presentation.common.FlowContainerFragment
import com.example.baseproject.presentation.common.MainApplication
import com.example.baseproject.presentation.common.ViewModelSuccess
import com.example.baseproject.presentation.common.clicks
import com.example.baseproject.presentation.common.scene.SceneView
import com.example.domain.model.PokemonInformation
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_pokemon_information_view.*
import kotlinx.android.synthetic.main.toolbar_view.*
import kotlinx.android.synthetic.main.view_empty_state.*
import java.util.*
import javax.inject.Inject


class PokemonInformationView : SceneView() {
    companion object {
        fun newInstance(pokemonName: String): PokemonInformationView =
            PokemonInformationView().apply {
                this.pokemonName = pokemonName
            }
    }

    private val pokemonNameBundleTag: String = "pokemonNameBundle"
    private val onCatchPokemon: PublishSubject<String> = PublishSubject.create<String>()
    private val onReleasePokemon: PublishSubject<String> = PublishSubject.create<String>()
    private var caughtPokemon: Boolean = false
    private var pokemonName: String? = null

    @Inject
    override lateinit var viewModel: PokemonInformationViewModel

    private val component: PokemonInformationComponent? by lazy {
        DaggerPokemonInformationComponent
            .builder()
            .pokemonInformationModule(PokemonInformationModule())
            .applicationComponent((activity?.application as? MainApplication)?.applicationComponent)
            .flowContainerComponent((parentFragment as? FlowContainerFragment)?.component)
            .build()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_information_view, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(pokemonNameBundleTag, pokemonName)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            pokemonName = it.getString(pokemonNameBundleTag)
        }

        toolbarTitleText.text = this.pokemonName?.toUpperCase(Locale.ROOT)
        setupAppBar(toolbar as Toolbar, true, isModal = false)

        observeLiveData()

        pokemonName?.let { pokemonName ->
            viewModel.getPokemonInformation(pokemonName)

            catchPokemonButton.clicks().doOnNext {
                if (caughtPokemon) {
                    onReleasePokemon.onNext(pokemonName)
                } else {
                    onCatchPokemon.onNext(pokemonName)
                }
                caughtPokemon = !caughtPokemon
                changeCatchButtonText(caughtPokemon)
            }.subscribe().addTo(disposables)
        }
    }

    override fun observeLiveData() {
        super.observeLiveData()

        with(viewModel) {
            pokemonInformationLiveData.observe(
                viewLifecycleOwner,
                Observer { pokemonInformationState ->
                    if (pokemonInformationState is ViewModelSuccess) {
                        displayPokemonInformation(pokemonInformationState.getData())
                    } else {
                        displayBlockingError()
                    }
                })

            catchPokemonLiveData.observe(viewLifecycleOwner, Observer {
                caughtPokemon = !caughtPokemon
                changeCatchButtonText(caughtPokemon)
            })
        }
    }

    private fun displayPokemonInformation(pokemonInformation: PokemonInformation) {
        val pokemonName = pokemonInformation.name
        caughtPokemon = pokemonInformation.caughtPokemon
        pokemonInformationNameText.text = pokemonName.toUpperCase(Locale.ROOT)

        changeCatchButtonText(caughtPokemon)

        pokemonInformation.frontSprite?.let { frontSprite ->
            Glide.with(this)
                .load(frontSprite)
                .into(frontImage)
        }

        catchPokemonButton.clicks().doOnNext {
            if (caughtPokemon) {
                viewModel.releasePokemon(pokemonName)
            } else {
                viewModel.catchPokemon(pokemonName)
            }
        }.subscribe().addTo(disposables)

        dismissBlockingError()
    }

    private fun displayBlockingError() {
        displayBlockingError(pokemonInformationContentLayout, errorLayout)
        tryAgainActionButton.clicks().doOnNext {
            pokemonName?.let {
                viewModel.getPokemonInformation(it)
            }
        }.subscribe().addTo(disposables)
    }

    private fun dismissBlockingError() {
        dismissBlockingError(pokemonInformationContentLayout, errorLayout)
    }

    private fun changeCatchButtonText(caughtPokemon: Boolean) {
        if (caughtPokemon) {
            catchPokemonButton.text = getText(R.string.pokemon_list_release_text)
        } else {
            catchPokemonButton.text = getText(R.string.pokemon_list_catch_text)
        }
    }
}