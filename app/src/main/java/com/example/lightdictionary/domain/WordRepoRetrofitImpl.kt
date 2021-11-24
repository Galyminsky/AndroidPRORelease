package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity
import io.reactivex.Single

class WordRepoRetrofitImpl(private val service: WordRetrofitService) : WordRepo {
    override fun getWord(src: String): Single<List<WordEntity>> = service.getWord(src)
}