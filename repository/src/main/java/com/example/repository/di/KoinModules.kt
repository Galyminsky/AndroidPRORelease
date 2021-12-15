package com.example.repository.di

import androidx.room.Room
import com.example.repository.*
import com.example.repository.retrofit.WordRetrofitService
import com.example.repository.room.HistoryDao
import com.example.repository.room.HistoryDatabase
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val RETROFIT = "Retrofit"
const val ROOM = "Room"

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

val databaseModule = module {
    single<HistoryDatabase> {
        Room.databaseBuilder(get(), HistoryDatabase::class.java, "history.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single<HistoryDao> { get<HistoryDatabase>().historyDao() }
    single<LoadingWordsRepo>(named(ROOM)) { WordRepoRoomImpl(get<HistoryDao>()) }
    single<SavingWordsRepo>(named(ROOM)) { WordRepoRoomImpl(get<HistoryDao>()) }
}
