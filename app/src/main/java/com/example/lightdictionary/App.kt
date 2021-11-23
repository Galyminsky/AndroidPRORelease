package com.example.lightdictionary

import android.app.Application
import android.content.Context
import com.example.lightdictionary.domain.WordRetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitService: WordRetrofitService by lazy {
        retrofit.create(WordRetrofitService::class.java)
    }

}

val Context.app get() = applicationContext as App