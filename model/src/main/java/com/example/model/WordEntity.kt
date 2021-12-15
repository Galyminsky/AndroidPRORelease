package com.example.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "words")
@TypeConverters(GsonConverter::class)
data class WordEntity(
    @PrimaryKey
    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "meanings")
    val meanings: List<MeaningsEntity>
) : Parcelable

@Parcelize
data class MeaningsEntity(
    val translation: TranslationEntity,
    val imageUrl: String,
    val transcription: String,
    val soundUrl: String?
) : Parcelable

@Parcelize
data class TranslationEntity(
    val text: String,
    val note: String?
) : Parcelable


