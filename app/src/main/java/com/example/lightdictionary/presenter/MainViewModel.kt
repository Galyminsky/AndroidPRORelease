package com.example.lightdictionary.presenter

import com.example.lightdictionary.data.LoadWordsState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val interactor: MainController.Interactor) : MainController.BaseViewModel() {
    private val disposable = CompositeDisposable()

    override fun onSearchScreenOpened() {
        searchLiveDataMutable.value = false
    }

    override fun onSearchClicked() {
        searchLiveDataMutable.value = true
    }

    override fun onGetInputWord(word: String) {
        disposable.add(
            interactor.getData(word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    loadStateLiveDataMutable.value = LoadWordsState.Loading
                }
                .subscribe { state ->
                    loadStateLiveDataMutable.value = state
                }
        )
    }
}