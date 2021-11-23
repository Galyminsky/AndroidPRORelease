package com.example.lightdictionary.domain

interface WordRepo {
    fun getWord(src: String)
}