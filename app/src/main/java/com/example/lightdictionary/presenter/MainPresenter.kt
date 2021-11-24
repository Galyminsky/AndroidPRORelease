package com.example.lightdictionary.presenter

import com.example.lightdictionary.data.LoadWordsState
import com.example.lightdictionary.data.LoadWordsState.Error
import com.example.lightdictionary.data.LoadWordsState.Success
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val interactor: MainController.Interactor) : MainController.Presenter {
    private var view: MainController.View? = null
    private val disposable = CompositeDisposable()

    override fun attachView(view: MainController.View) {
        this.view = view
    }

    override fun detachView() {
        disposable.clear()
        this.view = null
    }

    override fun onSearchClicked() {
        view?.showSearchInputScreen()
    }

    override fun onGetInputWord(word: String) {
        disposable.add(
            interactor.getData(word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { view?.showLoading(true) }
                .subscribe { state -> doOnSuccessLoading(state) }
        )
    }

    private fun doOnSuccessLoading(state: LoadWordsState) {
        when (state) {
            is Success -> {
                view?.showLoading(false)
                view?.showWords(state.words)
            }
            is Error -> {
                view?.showLoading(false)
                state.error.localizedMessage?.let {
                    view?.showError(it)
                }
            }
        }
    }
}