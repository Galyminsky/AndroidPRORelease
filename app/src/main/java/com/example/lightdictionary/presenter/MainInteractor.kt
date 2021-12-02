package com.example.lightdictionary.presenter

import com.example.lightdictionary.data.LoadWordsState
import com.example.lightdictionary.domain.WordRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class MainInteractor(private val wordRepo: WordRepo) : MainController.Interactor {
    private val behaviorSubject = BehaviorSubject.create<LoadWordsState>()

    override fun getData(src: String): Observable<LoadWordsState> {
        wordRepo.getWord(src)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { words ->
                    behaviorSubject.onNext(LoadWordsState.Success(words)) },
                { thr ->
                    behaviorSubject.onNext(LoadWordsState.Error(thr)) }
            )
        return behaviorSubject
    }
}