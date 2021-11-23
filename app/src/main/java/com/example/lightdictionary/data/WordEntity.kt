package com.example.lightdictionary.data

data class WordEntity(
    val text: String? = "",
    val meanings: List<MeaningsEntity>? = emptyList()
)

data class MeaningsEntity(
    val translation: TranslationEntity?,
    val imageUrl: String?
)

data class TranslationEntity(
    val text: String?
)
