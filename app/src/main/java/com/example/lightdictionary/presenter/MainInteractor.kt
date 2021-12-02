package com.example.lightdictionary.presenter

import com.example.lightdictionary.data.LoadWordsState
import com.example.lightdictionary.domain.WordRepo

class MainInteractor(private val wordRepo: WordRepo) : MainController.Interactor {
    override suspend fun getData(src: String): LoadWordsState {
        return try {
            val words = wordRepo.getWord(src)
            LoadWordsState.Success(words)
        } catch (thr: Throwable) {
            LoadWordsState.Error(thr)
        }
    }
}