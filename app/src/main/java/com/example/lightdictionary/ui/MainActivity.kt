package com.example.lightdictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lightdictionary.R
import com.example.lightdictionary.databinding.ActivityMainBinding
import com.example.lightdictionary.presenter.MainController
import com.example.lightdictionary.presenter.MainPresenter

private const val SEARCH_INPUT_FRAGMENT_TAG = "SEARCH_INPUT_FRAGMENT_TAG"

class MainActivity : AppCompatActivity(), MainController.View, SearchInputFragment.Controller {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val presenter: MainController.Presenter by lazy { MainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)

        binding.searchFab.setOnClickListener {
            presenter.onSearchClicked()
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showSearchInputScreen() {
        SearchInputFragment.newInstance().show(supportFragmentManager, SEARCH_INPUT_FRAGMENT_TAG)
    }

    override fun setNewWord(word: String) {
        presenter.onGetNewWord(word)
    }
}