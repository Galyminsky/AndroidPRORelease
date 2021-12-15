package com.example.lightdictionary.presenter.history

import androidx.lifecycle.viewModelScope
import com.example.model.LoadWordsState
import com.example.model.WordEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class HistoryViewModel(private val interactor: HistoryController.Interactor) : HistoryController.BaseViewModel() {
    private val coroutineScope = viewModelScope + CoroutineExceptionHandler { _, throwable ->
        loadStateLiveDataMutable.postValue(LoadWordsState.Error(throwable))
    }

    override fun onRecycleItemClicked(word: WordEntity) {
        detailLiveDataMutable.value = word
    }

    override fun onDetailScreenOpened() {
        detailLiveDataMutable.value = null
    }

    override fun onViewCreated() {
        loadStateLiveDataMutable.value = LoadWordsState.Loading
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                interactor.getData().collect {
                    loadStateLiveDataMutable.postValue(it)
                }
            }
        }
    }
}