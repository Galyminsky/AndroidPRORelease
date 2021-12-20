package com.example.lightdictionary.di

import com.example.lightdictionary.presenter.history.HistoryController
import com.example.lightdictionary.presenter.history.HistoryInteractor
import com.example.lightdictionary.presenter.history.HistoryViewModel
import com.example.lightdictionary.presenter.main.MainController
import com.example.lightdictionary.presenter.main.MainInteractor
import com.example.lightdictionary.presenter.main.MainViewModel
import com.example.lightdictionary.ui.HistoryActivity
import com.example.lightdictionary.ui.MainActivity
import com.example.repository.LoadingWordsRepo
import com.example.repository.SavingWordsRepo
import com.example.repository.di.RETROFIT
import com.example.repository.di.ROOM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    scope(named<MainActivity>()) {
        scoped<MainController.Interactor> {
            MainInteractor(get<LoadingWordsRepo>(named(RETROFIT)), get<SavingWordsRepo>(named(ROOM)))
        }
        viewModel<MainController.BaseViewModel> {
            MainViewModel(get<MainController.Interactor>())
        }
    }

    scope(named<HistoryActivity>()) {
        scoped<HistoryController.Interactor> {
            HistoryInteractor(get<LoadingWordsRepo>(named(ROOM)))
        }
        viewModel<HistoryController.BaseViewModel> {
            HistoryViewModel(get<HistoryController.Interactor>())
        }
    }
}

