package com.example.lightdictionary.presenter

import com.example.lightdictionary.data.LoadWordsState
import com.example.lightdictionary.domain.WordRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class MainInteractor(private val wordRepo: WordRepo) : MainController.Interactor {
    private val loadWordsStateFlow = MutableStateFlow<LoadWordsState>(LoadWordsState.Loading)

    override suspend fun getData(src: String): StateFlow<LoadWordsState> {
        return try {
            wordRepo.getWord(src).collect { words ->
                loadWordsStateFlow.value = LoadWordsState.Success(words)
            }
            loadWordsStateFlow
        } catch (thr: Throwable) {
            loadWordsStateFlow.value = LoadWordsState.Error(thr)
            loadWordsStateFlow
        }
    }
}