package com.example.lightdictionary.presenter

import com.example.lightdictionary.data.LoadWordsState
import kotlinx.coroutines.*

class MainViewModel(private val interactor: MainController.Interactor) : MainController.BaseViewModel() {

    private val coroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            loadStateLiveDataMutable.postValue(LoadWordsState.Error(throwable))
        })

    override fun onCleared() {
        coroutineScope.coroutineContext.cancelChildren()
        super.onCleared()
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
                loadStateLiveDataMutable.postValue(interactor.getData(word))
            }
        }
    }
}