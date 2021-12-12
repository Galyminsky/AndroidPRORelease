package com.example.lightdictionary.domain

import com.example.lightdictionary.data.HistoryEntity

interface SavingWordsRepo {
    suspend fun saveWord(word: HistoryEntity)
}