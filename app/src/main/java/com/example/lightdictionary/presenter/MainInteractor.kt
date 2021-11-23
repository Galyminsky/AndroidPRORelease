package com.example.lightdictionary.presenter

import com.example.lightdictionary.data.WordEntity
import com.example.lightdictionary.domain.WordRepo

class MainInteractor(private val wordRepo: WordRepo) : MainController.Interactor {
    override fun getData(src: String): WordEntity = wordRepo.getWord(src)
}