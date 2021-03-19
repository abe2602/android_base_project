package com.example.baseproject.data.repository

import com.example.baseproject.common.CatchPokemonDataObservable
import com.example.baseproject.data.cache.PokemonCDS
import com.example.baseproject.data.mappers.toDM
import com.example.baseproject.data.remote.PokemonRDS
import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.model.PokemonInformation
import com.example.domain.model.PokemonList
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonRDS: PokemonRDS,
    private val pokemonCDS: PokemonCDS,
    @CatchPokemonDataObservable private val catchPokemonDataObservable: PublishSubject<Unit>
) : PokemonDataRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonList> {
        return flow {
            val pokemonList = pokemonRDS.getPokemonList(limit, offset)

            emit(pokemonList.toDM())
        }
    }

    override fun getPokemonInformation(pokemonName: String): Single<PokemonInformation> =
        getCaughtPokemonList().flatMap { caughtPokemonList ->
            pokemonRDS.getPokemonInformation(pokemonName).map {
                it.toDM(caughtPokemonList.contains(it.name))
            }
        }

    override fun catchPokemon(pokemonName: String): Completable = pokemonCDS.getCaughtPokemonList()
        .flatMapCompletable { caughtPokemonList ->
            caughtPokemonList.add(pokemonName)
            pokemonCDS.upsertCaughtPokemon(caughtPokemonList)
        }.doOnComplete {
            catchPokemonDataObservable.onNext(Unit)
        }

    override fun releasePokemon(pokemonName: String): Completable =
        pokemonCDS.getCaughtPokemonList()
            .flatMapCompletable { caughtPokemonList ->
                caughtPokemonList.remove(pokemonName)
                pokemonCDS.upsertCaughtPokemon(caughtPokemonList)
            }.doOnComplete {
                catchPokemonDataObservable.onNext(Unit)
            }

    override fun getCaughtPokemonList(): Single<List<String>> =
        pokemonCDS.getCaughtPokemonList().map {
            it.toList()
        }
}