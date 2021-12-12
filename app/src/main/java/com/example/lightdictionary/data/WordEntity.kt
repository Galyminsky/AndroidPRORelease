package com.example.lightdictionary.data

import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "words")
@TypeConverters(Converters::class)
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

class Converters {
    @TypeConverter
    fun listToJson(value: List<MeaningsEntity>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<MeaningsEntity>::class.java).toList()
}
