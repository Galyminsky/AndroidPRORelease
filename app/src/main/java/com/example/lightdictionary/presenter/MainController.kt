package com.example.lightdictionary.presenter

import com.example.lightdictionary.data.LoadWordsState
import com.example.lightdictionary.data.WordEntity
import io.reactivex.Observable

class MainController {
    interface View {
        fun showSearchInputScreen()
        fun showError(s: String)
        fun showWords(words: List<WordEntity>)
        fun showLoading(isLoading: Boolean)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onSearchClicked()
        fun onGetInputWord(word: String)
    }

    interface Interactor {
        fun getData(src: String): Observable<LoadWordsState>
    }
}