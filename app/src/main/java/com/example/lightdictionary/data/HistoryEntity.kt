package com.example.lightdictionary.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "words")
data class HistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "translation")
    val translation: String,

    @ColumnInfo(name = "note")
    val note: String?,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "transcription")
    val transcription: String,

    @ColumnInfo(name = "soundUrl")
    val soundUrl: String?
) : Parcelable

