package com.example.lightdictionary.database

import androidx.room.*
import com.example.lightdictionary.data.WordEntity

@Dao
interface HistoryDao {
    @Query("SELECT * FROM words")
    suspend fun getAllWords(): List<WordEntity>

    @Query("SELECT * FROM words WHERE text LIKE :word")
    suspend fun getDataByWord(word: String): WordEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveWord(entity: WordEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveWords(entities: List<WordEntity>)

    @Update
    suspend fun updateWord(entity: WordEntity)

    @Delete
    suspend fun deleteWord(entity: WordEntity)
}