package com.example.lightdictionary.domain

import com.example.model.WordEntity
import kotlinx.coroutines.flow.Flow

interface LoadingWordsRepo {
    suspend fun getWord(src: String?) : Flow<List<WordEntity>>
}