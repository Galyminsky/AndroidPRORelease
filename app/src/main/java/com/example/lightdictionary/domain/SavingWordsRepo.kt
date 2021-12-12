package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity

interface SavingWordsRepo {
    suspend fun saveWord(word: WordEntity)
}