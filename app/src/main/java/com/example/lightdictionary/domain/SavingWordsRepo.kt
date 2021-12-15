package com.example.lightdictionary.domain

import com.example.model.WordEntity

interface SavingWordsRepo {
    suspend fun saveWord(word: WordEntity)
}