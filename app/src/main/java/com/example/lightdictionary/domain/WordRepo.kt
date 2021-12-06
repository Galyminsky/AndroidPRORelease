package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

abstract class WordRepo {
    abstract suspend fun getWord(src: String) : Flow<List<WordEntity>>

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): T {
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