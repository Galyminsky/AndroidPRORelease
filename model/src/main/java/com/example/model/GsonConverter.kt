package com.example.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class GsonConverter {
    @TypeConverter
    fun listToJson(value: List<MeaningsEntity>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<MeaningsEntity>::class.java).toList()
}