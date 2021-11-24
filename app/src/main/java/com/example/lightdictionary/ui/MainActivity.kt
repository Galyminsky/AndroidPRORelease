package com.example.lightdictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lightdictionary.R
import com.example.lightdictionary.app
import com.example.lightdictionary.data.WordEntity
import com.example.lightdictionary.databinding.ActivityMainBinding
import com.example.lightdictionary.presenter.MainController
import com.example.lightdictionary.presenter.MainPresenter

private const val SEARCH_INPUT_FRAGMENT_TAG = "SEARCH_INPUT_FRAGMENT_TAG"

class MainActivity : AppCompatActivity(), MainController.View, SearchInputFragment.Controller {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val presenter: MainController.Presenter by lazy { MainPresenter(app.mainInteractor) }
    private val adapter: MainAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)
        binding.wordsRecyclerView.adapter = adapter
        binding.wordsRecyclerView.layoutManager = LinearLayoutManager(this)

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

    override fun showError(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    override fun showWords(words: List<WordEntity>) {
        adapter.updateList(words)
    }

    override fun showLoading(isLoading: Boolean) {
        binding.progressLayout.visibility = when (isLoading) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    override fun setNewWord(word: String) {
        presenter.onGetInputWord(word)
    }
}