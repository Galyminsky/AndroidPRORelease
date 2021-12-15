package com.example.lightdictionary.presenter.main

import com.example.model.LoadWordsState
import com.example.model.WordEntity
import com.example.repository.LoadingWordsRepo
import com.example.repository.SavingWordsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class MainInteractor(
    private val loadingRepo: LoadingWordsRepo,
    private val savingRepo: SavingWordsRepo
) : MainController.Interactor {
    private val loadWordsStateFlow = MutableStateFlow<LoadWordsState>(LoadWordsState.Loading)

    override suspend fun getData(src: String): StateFlow<LoadWordsState> {
        return try {
            loadingRepo.getWord(src).collect { words ->
                loadWordsStateFlow.value = LoadWordsState.Success(words)
            }
            loadWordsStateFlow
        } catch (thr: Throwable) {
            loadWordsStateFlow.value = LoadWordsState.Error(thr)
            loadWordsStateFlow
        }
    }

    override suspend fun saveData(word: WordEntity) {
        savingRepo.saveWord(word)
    }
}