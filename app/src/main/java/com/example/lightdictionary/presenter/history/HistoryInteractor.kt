package com.example.lightdictionary.presenter.history

import com.example.model.LoadWordsState
import com.example.lightdictionary.domain.LoadingWordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class HistoryInteractor (private val wordRepo: LoadingWordsRepo) : HistoryController.Interactor {
    private val loadWordsStateFlow = MutableStateFlow<LoadWordsState>(LoadWordsState.Loading)

    override suspend fun getData(): StateFlow<LoadWordsState> {
        return try {
            wordRepo.getWord(null).collect { words ->
                loadWordsStateFlow.value = LoadWordsState.Success(words)
            }
            loadWordsStateFlow
        } catch (thr: Throwable) {
            loadWordsStateFlow.value = LoadWordsState.Error(thr)
            loadWordsStateFlow
        }
    }
}