package com.example.lightdictionary.domain

import com.example.lightdictionary.data.WordEntity
import com.example.lightdictionary.database.HistoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WordRepoRoomImpl(private val dao: HistoryDao) : LoadingWordsRepo, SavingWordsRepo {
    override suspend fun getWord(src: String): Flow<List<WordEntity>> {
        return flow {
            emit (dao.getAllWords())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveWord(word: WordEntity) {
        dao.saveWord(word)
    }
}