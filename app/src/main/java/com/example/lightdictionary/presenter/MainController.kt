package com.example.lightdictionary.presenter

class MainController {
    interface View {
        fun showSearchInputScreen()
    }
    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onSearchClicked()
        fun onGetNewWord(word: String)
    }
}