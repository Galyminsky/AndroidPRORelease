package com.example.lightdictionary.presenter

class MainPresenter : MainController.Presenter {
    private var view: MainController.View? = null

    override fun attachView(view: MainController.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onSearchClicked() {
        view?.showSearchInputScreen()
    }

    override fun onGetNewWord(word: String) {

    }
}