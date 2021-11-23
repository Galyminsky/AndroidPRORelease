package com.example.lightdictionary.presenter

import android.util.Log
import com.example.lightdictionary.data.WordEntity
import com.example.lightdictionary.domain.WordRepo
import com.example.lightdictionary.domain.WordRepoRetrofitImpl
import com.example.lightdictionary.domain.WordRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(service: WordRetrofitService) : MainController.Presenter {
    private var view: MainController.View? = null

    private val callback = object : Callback<List<WordEntity>> {
        override fun onResponse(call: Call<List<WordEntity>>, response: Response<List<WordEntity>>) {
            val words: List<WordEntity>? = response.body()
            if (response.isSuccessful && words != null) {
                words[0].meanings?.get(0)?.translation?.text?.let { view?.showWord(it) }
            } else {
                view?.showError("SERVER_ERROR")
            }

        }

        override fun onFailure(call: Call<List<WordEntity>>, t: Throwable) {
            Log.d("@@@ onFailure", t.message.toString())
        }
    }

    private val wordRepo: WordRepo by lazy { WordRepoRetrofitImpl(service, callback) }
    private val interactor: MainController.Interactor by lazy { MainInteractor(wordRepo) }

    override fun attachView(view: MainController.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onSearchClicked() {
        view?.showSearchInputScreen()
    }

    override fun onGetInputWord(word: String) {
        interactor.getData(word)
    }
}