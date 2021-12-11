package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class WordRepoRetrofitImpl(private val service: WordRetrofitService) : LoadingWordsRepo {
    override suspend fun getWord(src: String?): Flow<List<WordEntity>> {
        return flow {
            emit (safeApiCall { service.getWord(src!!) })
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun safeApiCall(apiCall: suspend () -> Response<List<WordEntity>>): List<WordEntity> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return body
                }
            }
            throw IllegalAccessException("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            throw e
        }
    }
}