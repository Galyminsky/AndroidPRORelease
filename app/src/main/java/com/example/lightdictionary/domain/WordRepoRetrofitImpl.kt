package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity

class WordRepoRetrofitImpl(private val service: WordRetrofitService) : WordRepo {
    override suspend fun getWord(src: String): List<WordEntity> = service.getWord(src).await()
}