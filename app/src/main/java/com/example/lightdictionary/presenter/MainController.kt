package com.example.lightdictionary.presenter

class MainController {
    interface View {
        fun showSearchInputScreen()
        fun showError(s: String)
        abstract fun showWord(s: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onSearchClicked()
        fun onGetInputWord(word: String)
    }

    interface Interactor {
        fun getData(src: String)
    }
}