package com.example.baseproject.presentation.pokemon_information

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.baseproject.R
import com.example.baseproject.common.DisposableHolder
import com.example.baseproject.common.DisposableHolderDelegate
import com.example.baseproject.presentation.common.BackButtonListener
import com.example.baseproject.presentation.common.FlowContainerFragment
import com.example.baseproject.presentation.common.PokedexApplication
import com.example.baseproject.presentation.pokemonlist.DaggerPokemonListComponent
import com.example.baseproject.presentation.pokemonlist.PokemonListComponent
import com.example.baseproject.presentation.pokemonlist.PokemonListModule
import com.example.baseproject.presentation.pokemonlist.PokemonListView
import com.example.domain.model.PokemonInformation
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_pokemon_information_view.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class PokemonInformationView : Fragment(), PokemonInformationUi,
    DisposableHolder by DisposableHolderDelegate(),
    BackButtonListener {

    companion object {
        fun newInstance(pokemonName: String): PokemonInformationView = PokemonInformationView().apply {
            this.pokemonName = pokemonName
        }
    }

    override val onViewCreated: PublishSubject<Unit> = PublishSubject.create<Unit>()
    override val onReceivedPokemonName: PublishSubject<String> = PublishSubject.create<String>()

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var presenter: PokemonInformationPresenter

    private val component: PokemonInformationComponent? by lazy {
        DaggerPokemonInformationComponent
            .builder()
            .pokemonInformationModule(PokemonInformationModule(this))
            .applicationComponent((activity?.application as? PokedexApplication)?.applicationComponent)
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
        onViewCreated.onNext(Unit)
        onReceivedPokemonName.onNext(pokemonName)
    }

    override fun displayPokemonInformation(pokemonInformation: PokemonInformation) {
        pokemonInformationNameText.text = pokemonInformation.name
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }

}