package com.example.lightdictionary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lightdictionary.data.HistoryEntity

@Dao
interface HistoryDao {
    @Query("SELECT * FROM words")
    fun getAllWords(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveWord(entity: HistoryEntity)
}