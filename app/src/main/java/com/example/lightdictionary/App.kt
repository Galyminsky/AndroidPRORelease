package com.example.lightdictionary

import android.app.Application
import com.example.lightdictionary.di.retrofitModule
import com.example.lightdictionary.di.viewModelModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(retrofitModule, viewModelModule)
        }
    }
}