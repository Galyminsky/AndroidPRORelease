package com.example.lightdictionary.presenter

import com.example.lightdictionary.domain.WordRepo

class MainPresenter(private val wordRepo: WordRepo) : MainController.Presenter {
    private var view: MainController.View? = null
    private val interactor: MainController.Interactor by lazy { MainInteractor(wordRepo) }

    override fun attachView(view: MainController.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onSearchClicked() {
        view?.showSearchInputScreen()
    }

    override fun onGetInputWord(word: String) {
        interactor.getData(word)
    }
}