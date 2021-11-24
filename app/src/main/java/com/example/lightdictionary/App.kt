package com.example.lightdictionary

import android.app.Application
import android.content.Context
import com.example.lightdictionary.domain.WordRepo
import com.example.lightdictionary.domain.WordRepoRetrofitImpl
import com.example.lightdictionary.domain.WordRetrofitService
import com.example.lightdictionary.presenter.MainController
import com.example.lightdictionary.presenter.MainInteractor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private val retrofitService: WordRetrofitService by lazy {
        retrofit.create(WordRetrofitService::class.java)
    }

    private val wordRepo: WordRepo by lazy { WordRepoRetrofitImpl(retrofitService) }
    val mainInteractor: MainController.Interactor by lazy { MainInteractor(wordRepo) }
}

val Context.app get() = applicationContext as App