package com.example.lightdictionary.presenter

import com.example.lightdictionary.data.WordEntity

class MainController {
    interface View {
        fun showSearchInputScreen()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onSearchClicked()
        fun onGetInputWord(word: String)
    }

    interface Interactor {
        fun getData(word: String) : WordEntity
    }
}