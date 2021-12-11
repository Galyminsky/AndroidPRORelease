package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LoadingWordsRepo {
    suspend fun getWord(src: String) : Flow<List<WordEntity>>
}