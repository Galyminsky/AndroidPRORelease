package com.example.lightdictionary.data

import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "words")
@TypeConverters(Converters::class)
data class HistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "meanings")
    val meanings: List<MeaningsEntity>
) : Parcelable