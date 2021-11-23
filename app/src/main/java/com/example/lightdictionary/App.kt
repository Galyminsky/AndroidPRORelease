package com.example.lightdictionary

import android.app.Application
import android.content.Context
import com.example.lightdictionary.domain.WordRepo
import com.example.lightdictionary.domain.WordRepoRetrofitImpl

class App : Application() {
    val wordRepo: WordRepo by lazy { WordRepoRetrofitImpl() }
}

val Context.app get() = applicationContext as App