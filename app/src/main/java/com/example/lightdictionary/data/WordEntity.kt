package com.example.lightdictionary.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WordEntity(
    val text: String = "",
    val meanings: List<MeaningsEntity> = emptyList()
) : Parcelable

@Parcelize
data class MeaningsEntity(
    val translation: TranslationEntity = TranslationEntity(),
    val imageUrl: String = "",
    val transcription: String = "",
    val soundUrl: String? = null
) : Parcelable

@Parcelize
data class TranslationEntity(
    val text: String = "",
    val note: String? = ""
) : Parcelable
