package com.example.lightdictionary.presenter.main

import androidx.lifecycle.viewModelScope
import com.example.model.LoadWordsState
import com.example.model.WordEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainViewModel(private val interactor: MainController.Interactor) : MainController.BaseViewModel() {

    private val coroutineScope = viewModelScope + CoroutineExceptionHandler { _, throwable ->
        loadStateLiveDataMutable.postValue(LoadWordsState.Error(throwable))
    }

    override fun onSearchScreenOpened() {
        searchLiveDataMutable.value = false
    }

    override fun onRecycleItemClicked(word: WordEntity) {
        detailLiveDataMutable.value = word
        coroutineScope.launch {
            withContext(Dispatchers.IO) { interactor.saveData(word) }
        }
    }

    override fun onDetailScreenOpened() {
        detailLiveDataMutable.value = null
    }

    override fun onSearchClicked() {
        searchLiveDataMutable.value = true
    }

    override fun onGetInputWord(word: String) {
        loadStateLiveDataMutable.value = LoadWordsState.Loading
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                interactor.getData(word).collect {
                    loadStateLiveDataMutable.postValue(it)
                }
            }
        }
    }
}