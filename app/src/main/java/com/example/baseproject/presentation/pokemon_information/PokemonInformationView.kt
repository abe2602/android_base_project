package com.example.baseproject.presentation.pokemon_information

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.baseproject.R
import com.example.baseproject.presentation.common.FlowContainerFragment
import com.example.baseproject.presentation.common.MainApplication
import com.example.baseproject.presentation.common.scene.SceneView
import com.example.domain.model.PokemonInformation
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_pokemon_information_view.*
import kotlinx.android.synthetic.main.toolbar_view.*
import java.util.*
import javax.inject.Inject


class PokemonInformationView : SceneView(), PokemonInformationUi{

    companion object {
        fun newInstance(pokemonName: String): PokemonInformationView =
            PokemonInformationView().apply {
                this.pokemonName = pokemonName
            }
    }

    override val onReceivedPokemonName: PublishSubject<String> = PublishSubject.create<String>()

    @Inject
    lateinit var presenter: PokemonInformationPresenter

    private val component: PokemonInformationComponent? by lazy {
        DaggerPokemonInformationComponent
            .builder()
            .pokemonInformationModule(PokemonInformationModule(this))
            .applicationComponent((activity?.application as? MainApplication)?.applicationComponent)
            .flowContainerComponent((parentFragment as? FlowContainerFragment)?.component)
            .build()
    }

    private lateinit var pokemonName: String

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarTitleText.text = this.pokemonName.toUpperCase(Locale.ROOT)
        onViewCreated.onNext(Unit)
        onReceivedPokemonName.onNext(pokemonName)
    }

    override fun displayPokemonInformation(pokemonInformation: PokemonInformation) {
        pokemonInformationNameText?.let {
            it.text = pokemonInformation.name

            Glide.with(this)
                .load(pokemonInformation.frontSprite)
                .into(frontImage)
        }
    }
}