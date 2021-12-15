package com.example.repository

import com.example.model.WordEntity

interface SavingWordsRepo {
    suspend fun saveWord(word: WordEntity)
}