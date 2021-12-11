package com.example.lightdictionary.di

import androidx.room.Room
import com.example.lightdictionary.BuildConfig
import com.example.lightdictionary.database.HistoryDao
import com.example.lightdictionary.database.HistoryDatabase
import com.example.lightdictionary.domain.*
import com.example.lightdictionary.presenter.MainController
import com.example.lightdictionary.presenter.MainInteractor
import com.example.lightdictionary.presenter.MainViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val RETROFIT = "Retrofit"
private const val ROOM = "Room"

val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
    single<WordRetrofitService> { get<Retrofit>().create(WordRetrofitService::class.java) }
    single<LoadingWordsRepo>(named(RETROFIT)) { WordRepoRetrofitImpl(get<WordRetrofitService>()) }
}

val viewModelModule = module {
    factory<MainController.Interactor> { MainInteractor(get<LoadingWordsRepo>(named(RETROFIT))) }
    factory<MainController.BaseViewModel> { MainViewModel(get<MainController.Interactor>()) }
}

val databaseModule = module {
    single<HistoryDatabase> {
        Room.databaseBuilder(get(), HistoryDatabase::class.java, "history.db").build()
    }
    single<HistoryDao> { get<HistoryDatabase>().historyDao() }
    single<LoadingWordsRepo>(named(ROOM)) { WordRepoRoomImpl(get<HistoryDao>()) }
    single<SavingWordsRepo>(named(ROOM)) { WordRepoRoomImpl(get<HistoryDao>()) }
}
