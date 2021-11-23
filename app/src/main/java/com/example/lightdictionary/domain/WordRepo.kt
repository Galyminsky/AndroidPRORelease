package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity

interface WordRepo {
    fun getWord(src: String) : WordEntity
}