package com.example.baseproject.presentation.common

import androidx.room.Room
import com.example.baseproject.common.CatchPokemonDataObservable
import com.example.baseproject.data.cache.PokemonCdsDao
import com.example.baseproject.data.cache.PokemonDatabase
import com.example.baseproject.data.remote.PokemonRDS
import com.example.baseproject.data.repository.PokemonRepository
import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.usecase.BackgroundScheduler
import com.example.domain.usecase.MainScheduler
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val mainApplication: MainApplication) {
    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        val baseUrl = "https://pokeapi.co/api/v2/"
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @BackgroundScheduler
    fun backgroundThreadScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    @MainScheduler
    fun mainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    fun getPokemonRDS(retrofit: Retrofit): PokemonRDS = retrofit.create(PokemonRDS::class.java)

    @Provides
    fun getPokemonRepository(
        pokemonRepository: PokemonRepository
    ): PokemonDataRepository = pokemonRepository

    @Singleton
    @Provides
    fun getPokemonDatabase(): PokemonDatabase = Room.databaseBuilder(
        mainApplication,
        PokemonDatabase::class.java,
        "pokemon-db"
    ).build()

    @Singleton
    @Provides
    fun getPokemonCdsDao(pokemonDatabase: PokemonDatabase): PokemonCdsDao = pokemonDatabase.pokemonCdsDao()
}

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {
    fun getPokemonDatabase(): PokemonDatabase

    fun getPokemonCdsDao(): PokemonCdsDao

    fun getPokemonRepository(): PokemonDataRepository

    @BackgroundScheduler
    fun backgroundThreadScheduler(): Scheduler

    @MainScheduler
    fun mainThreadScheduler(): Scheduler
}