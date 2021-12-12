package com.example.lightdictionary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lightdictionary.data.WordEntity

@Database(entities = [WordEntity::class], version = 3)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}