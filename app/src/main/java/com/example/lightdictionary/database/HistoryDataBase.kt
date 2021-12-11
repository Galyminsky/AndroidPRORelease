package com.example.lightdictionary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lightdictionary.data.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}