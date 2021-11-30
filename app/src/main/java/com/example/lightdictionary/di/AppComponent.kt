package com.example.lightdictionary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lightdictionary.BuildConfig
import com.example.lightdictionary.domain.WordRepo
import com.example.lightdictionary.domain.WordRepoRetrofitImpl
import com.example.lightdictionary.domain.WordRetrofitService
import com.example.lightdictionary.presenter.MainController
import com.example.lightdictionary.presenter.MainInteractor
import com.example.lightdictionary.presenter.MainViewModel
import com.example.lightdictionary.ui.MainActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

const val RETROFIT = "Retrofit"

@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): WordRetrofitService =
        retrofit.create(WordRetrofitService::class.java)

    @Provides
    @Singleton
    @Named(RETROFIT)
    fun provideWordRepo(retrofitService: WordRetrofitService): WordRepo =
        WordRepoRetrofitImpl(retrofitService)
}

@Module
class InteractorModule {
    @Provides
    fun provideMainInteractor(@Named(RETROFIT) wordRepo: WordRepo): MainController.Interactor =
        MainInteractor(wordRepo)
}

@Module
class ViewModelModule {
    @Provides
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(mainViewModel: MainViewModel): ViewModel = mainViewModel
}

@Singleton
@Component(modules = [RetrofitModule::class, InteractorModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}