package com.example.lightdictionary.di

import com.example.lightdictionary.presenter.history.HistoryController
import com.example.lightdictionary.presenter.history.HistoryInteractor
import com.example.lightdictionary.presenter.history.HistoryViewModel
import com.example.lightdictionary.presenter.main.MainController
import com.example.lightdictionary.presenter.main.MainInteractor
import com.example.lightdictionary.presenter.main.MainViewModel
import com.example.repository.LoadingWordsRepo
import com.example.repository.SavingWordsRepo
import com.example.repository.di.RETROFIT
import com.example.repository.di.ROOM
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    factory<MainController.Interactor> {
        MainInteractor(get<LoadingWordsRepo>(named(RETROFIT)), get<SavingWordsRepo>(named(ROOM)))
    }
    factory<MainController.BaseViewModel> { MainViewModel(get<MainController.Interactor>()) }

    factory<HistoryController.Interactor> { HistoryInteractor(get<LoadingWordsRepo>(named(ROOM))) }
    factory<HistoryController.BaseViewModel> { HistoryViewModel(get<HistoryController.Interactor>()) }
}

