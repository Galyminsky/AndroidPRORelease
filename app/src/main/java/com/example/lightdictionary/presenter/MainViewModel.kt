package com.example.lightdictionary.presenter

import androidx.lifecycle.viewModelScope
import com.example.lightdictionary.data.LoadWordsState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainViewModel(private val interactor: MainController.Interactor) : MainController.BaseViewModel() {

    private val coroutineScope = viewModelScope + CoroutineExceptionHandler { _, throwable ->
        loadStateLiveDataMutable.postValue(LoadWordsState.Error(throwable))
    }

    override fun onSearchScreenOpened() {
        searchLiveDataMutable.value = false
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