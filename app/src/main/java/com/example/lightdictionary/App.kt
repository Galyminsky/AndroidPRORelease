package com.example.lightdictionary

import android.app.Application
import android.content.Context
import com.example.lightdictionary.di.AppComponent
import com.example.lightdictionary.di.DaggerAppComponent

class App : Application() {
    companion object {
        var daggerComponent: AppComponent = DaggerAppComponent.builder().build()
    }
}

val Context.app get() = applicationContext as App