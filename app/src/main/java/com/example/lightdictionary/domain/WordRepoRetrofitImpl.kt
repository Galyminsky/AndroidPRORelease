package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WordRepoRetrofitImpl(private val service: WordRetrofitService) : WordRepo() {
    override suspend fun getWord(src: String): Flow<List<WordEntity>> {
        return flow {
            emit (safeApiCall { service.getWord(src) })
        }.flowOn(Dispatchers.IO)
    }
}