package com.example.lightdictionary.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightdictionary.data.LoadWordsState
import io.reactivex.Observable

class MainController {
    interface View {
        fun showSearchInputScreen()
        fun renderLoadState(state: LoadWordsState)
    }

    interface Interactor {
        fun getData(src: String): Observable<LoadWordsState>
    }

    abstract class BaseViewModel(
        protected var loadStateLiveDataMutable: MutableLiveData<LoadWordsState> = MutableLiveData(),
        protected var searchLiveDataMutable: MutableLiveData<Boolean> = MutableLiveData(false),
        val loadStateLiveData: LiveData<LoadWordsState> = loadStateLiveDataMutable,
        val searchLiveData: LiveData<Boolean> = searchLiveDataMutable
    ) : ViewModel() {
        abstract fun onSearchClicked()
        abstract fun onGetInputWord(word: String)
        abstract fun initViewModel(interactor: Interactor)
        abstract fun onSearchScreenOpened()
    }
}