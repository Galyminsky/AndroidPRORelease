package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity

interface WordRepo {
    suspend fun getWord(src: String) : List<WordEntity>
}