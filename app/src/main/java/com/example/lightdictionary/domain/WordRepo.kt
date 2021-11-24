package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity
import io.reactivex.Single

interface WordRepo {
    fun getWord(src: String) : Single<List<WordEntity>>
}