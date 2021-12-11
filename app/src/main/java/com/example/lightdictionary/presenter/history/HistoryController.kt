package com.example.lightdictionary.presenter.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lightdictionary.data.LoadWordsState
import com.example.lightdictionary.data.WordEntity
import kotlinx.coroutines.flow.StateFlow

class HistoryController {
    interface View {
        fun renderLoadState(state: LoadWordsState)
        fun renderDetailData(word: WordEntity?)
    }

    interface Interactor {
        suspend fun getData(): StateFlow<LoadWordsState>
    }

    abstract class BaseViewModel(
        protected var loadStateLiveDataMutable: MutableLiveData<LoadWordsState> = MutableLiveData(),
        protected var detailLiveDataMutable: MutableLiveData<WordEntity?> = MutableLiveData(null),
        val loadStateLiveData: LiveData<LoadWordsState> = loadStateLiveDataMutable,
        val detailLiveData: LiveData<WordEntity?> = detailLiveDataMutable
    ) : ViewModel() {
        abstract fun onRecycleItemClicked(word: WordEntity)
        abstract fun onDetailScreenOpened()
        abstract fun onViewCreated()
    }
}