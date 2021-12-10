package com.example.lightdictionary.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightdictionary.data.LoadWordsState
import com.example.lightdictionary.data.WordEntity
import kotlinx.coroutines.flow.StateFlow

class MainController {
    interface View {
        fun renderLoadState(state: LoadWordsState)
        fun renderSearchData(state: Boolean)
        fun renderDetailData(word: WordEntity?)
    }

    interface Interactor {
        suspend fun getData(src: String): StateFlow<LoadWordsState>
    }

    abstract class BaseViewModel(
        protected var loadStateLiveDataMutable: MutableLiveData<LoadWordsState> = MutableLiveData(),
        protected var searchLiveDataMutable: MutableLiveData<Boolean> = MutableLiveData(false),
        protected var detailLiveDataMutable: MutableLiveData<WordEntity?> = MutableLiveData(null),
        val loadStateLiveData: LiveData<LoadWordsState> = loadStateLiveDataMutable,
        val searchLiveData: LiveData<Boolean> = searchLiveDataMutable,
        val detailLiveData: LiveData<WordEntity?> = detailLiveDataMutable
    ) : ViewModel() {
        abstract fun onSearchClicked()
        abstract fun onGetInputWord(word: String)
        abstract fun onSearchScreenOpened()
        abstract fun onRecycleItemClicked(word: WordEntity)
        abstract fun onDetailScreenOpened()
    }
}