package com.example.lightdictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lightdictionary.R
import com.example.lightdictionary.app
import com.example.lightdictionary.data.LoadWordsState
import com.example.lightdictionary.data.WordEntity
import com.example.lightdictionary.databinding.ActivityMainBinding
import com.example.lightdictionary.presenter.MainController
import com.example.lightdictionary.presenter.MainViewModel

private const val SEARCH_INPUT_FRAGMENT_TAG = "SEARCH_INPUT_FRAGMENT_TAG"

class MainActivity : AppCompatActivity(), MainController.View, SearchInputFragment.Controller {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainController.BaseViewModel by viewModels<MainViewModel>()
    private val adapter: MainAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.initViewModel(app.mainInteractor)
        viewModel.searchLiveData.observe(this) {
            if (it) {
                showSearchInputScreen()
                viewModel.onSearchScreenOpened()
            }
        }
        viewModel.loadStateLiveData.observe(this) { renderLoadState(it) }

        binding.wordsRecyclerView.adapter = adapter
        binding.wordsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.searchFab.setOnClickListener { viewModel.onSearchClicked() }
    }

    override fun renderLoadState(state: LoadWordsState) {
        when (state) {
            is LoadWordsState.Success -> {
                showLoading(false)
                showWords(state.words)
            }
            is LoadWordsState.Error -> {
                showLoading(false)
                state.error.localizedMessage?.let { showError(it) }
                    ?: showError(state.error.message.toString())
            }
            is LoadWordsState.Loading -> {
                showLoading(true)
            }
        }
    }

    override fun showSearchInputScreen() {
        SearchInputFragment.newInstance().show(supportFragmentManager, SEARCH_INPUT_FRAGMENT_TAG)
    }

    private fun showError(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun showWords(words: List<WordEntity>) {
        adapter.updateList(words)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressLayout.visibility = when (isLoading) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    override fun setNewWord(word: String) {
        viewModel.onGetInputWord(word)
    }
}