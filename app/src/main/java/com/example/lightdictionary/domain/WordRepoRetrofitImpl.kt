package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity
import retrofit2.Callback

class WordRepoRetrofitImpl(
    private val service: WordRetrofitService,
    private val callback: Callback<List<WordEntity>>
) : WordRepo {
    override fun getWord(src: String) {
        service.getWord(src).enqueue(callback)
    }
}