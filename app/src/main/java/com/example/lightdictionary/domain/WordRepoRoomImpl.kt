package com.example.lightdictionary.domain

import com.example.lightdictionary.data.HistoryEntity
import com.example.lightdictionary.data.MeaningsEntity
import com.example.lightdictionary.data.TranslationEntity
import com.example.lightdictionary.data.WordEntity
import com.example.lightdictionary.database.HistoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WordRepoRoomImpl(private val dao: HistoryDao) : LoadingWordsRepo, SavingWordsRepo {
    override suspend fun getWord(src: String?): Flow<List<WordEntity>> {
        return flow {
            emit (
                dao.getAllWords().map {
                    WordEntity(
                        it.word,
                        listOf(
                            MeaningsEntity(
                                TranslationEntity(it.translation, it.note),
                                it.imageUrl,
                                it.transcription,
                                it.soundUrl)
                        )
                    )
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveWord(word: WordEntity) {
        dao.saveWord(
            HistoryEntity(
                word.text,
                word.meanings[0].translation.text,
                word.meanings[0].translation.note,
                word.meanings[0].imageUrl,
                word.meanings[0].transcription,
                word.meanings[0].soundUrl
            )
        )
    }
}