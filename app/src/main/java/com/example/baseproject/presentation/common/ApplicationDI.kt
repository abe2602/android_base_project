package com.example.baseproject.presentation.common

import androidx.room.Room
import com.example.baseproject.data.cache.PokemonCdsDao
import com.example.baseproject.data.cache.PokemonDatabase
import com.example.baseproject.data.remote.PokemonRDS
import com.example.baseproject.data.repository.PokemonRepository
import com.example.domain.datarepository.PokemonDataRepository
import dagger.Component
import dagger.Module
import dagger.Provides
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
}