package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity

class WordRepoRetrofitImpl : WordRepo {
    override fun getWord(src: String) : WordEntity = WordEntity()
}