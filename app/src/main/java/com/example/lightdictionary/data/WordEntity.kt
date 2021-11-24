package com.example.lightdictionary.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordEntity(
    val text: String = "",
    val meanings: List<MeaningsEntity> = emptyList()
) : Parcelable

@Parcelize
data class MeaningsEntity(
    val translation: TranslationEntity = TranslationEntity(),
    val imageUrl: String = ""
) : Parcelable

@Parcelize
data class TranslationEntity(
    val text: String = ""
) : Parcelable
