package com.example.baseproject.presentation.common

import com.example.baseproject.data.cache.PokemonCDS
import com.example.baseproject.data.remote.PokemonRDS
import com.example.baseproject.data.repository.PokemonRepository
import com.example.domain.datarepository.PokemonDataRepository
import com.example.domain.usecase.BackgroundScheduler
import com.example.domain.usecase.MainScheduler
import com.pacoworks.rxpaper2.RxPaperBook
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule {
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
    @Singleton
    fun pokemonCDS(rxPaperBook: RxPaperBook): PokemonCDS = PokemonCDS(rxPaperBook)

    @Provides
    @Singleton
    fun getRxPaper(): RxPaperBook = RxPaperBook.with()

    @Provides
    fun getPokemonRepository(pokemonRDS: PokemonRDS, pokemonCDS: PokemonCDS): PokemonDataRepository =
        PokemonRepository(pokemonRDS, pokemonCDS)
}

@Component(modules = [ApplicationModule::class])
@Singleton
interface ApplicationComponent {
    fun getPokemonRepository(): PokemonDataRepository

    @BackgroundScheduler
    fun backgroundThreadScheduler(): Scheduler

    @MainScheduler
    fun mainThreadScheduler(): Scheduler
}