package com.example.lightdictionary.di

import com.example.lightdictionary.BuildConfig
import com.example.lightdictionary.domain.WordRepo
import com.example.lightdictionary.domain.WordRepoRetrofitImpl
import com.example.lightdictionary.domain.WordRetrofitService
import com.example.lightdictionary.presenter.MainController
import com.example.lightdictionary.presenter.MainInteractor
import com.example.lightdictionary.presenter.MainViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val RETROFIT = "Retrofit"

val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
    single<WordRetrofitService> { get<Retrofit>().create(WordRetrofitService::class.java) }
    single<WordRepo>(named(RETROFIT)) { WordRepoRetrofitImpl(get<WordRetrofitService>()) }
}

val viewModelModule = module {
    factory<MainController.Interactor> { MainInteractor(get<WordRepo>(named(RETROFIT))) }
    factory<MainController.BaseViewModel> { MainViewModel(get<MainController.Interactor>()) }
}
