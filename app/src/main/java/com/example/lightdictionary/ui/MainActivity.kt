package com.example.lightdictionary.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lightdictionary.R
import com.example.lightdictionary.data.LoadWordsState
import com.example.lightdictionary.data.WordEntity
import com.example.lightdictionary.databinding.ActivityMainBinding
import com.example.lightdictionary.presenter.main.MainController
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SEARCH_INPUT_FRAGMENT_TAG = "SEARCH_INPUT_FRAGMENT_TAG"

class MainActivity : AppCompatActivity(), MainController.View, SearchInputFragment.Controller {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val model: MainController.BaseViewModel by viewModel()
    private val adapter: MainAdapter by lazy { MainAdapter(::onItemClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.searchLiveData.observe(this) { renderSearchData(it) }
        model.loadStateLiveData.observe(this) { renderLoadState(it) }
        model.detailLiveData.observe(this) { renderDetailData(it) }

        binding.wordsRecyclerView.adapter = adapter
        binding.wordsRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.searchFab.setOnClickListener { model.onSearchClicked() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> false
        }
    }

    override fun renderSearchData(state: Boolean) {
        if (state) {
            showSearchInputScreen()
            model.onSearchScreenOpened()
        }
    }

    override fun renderDetailData(word: WordEntity?) {
        if (word != null) {
            showDetailScreen(word)
            model.onDetailScreenOpened()
            binding.searchFab.hide()
        }
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

    private fun showSearchInputScreen() {
        SearchInputFragment.newInstance().show(supportFragmentManager, SEARCH_INPUT_FRAGMENT_TAG)
    }

    private fun showDetailScreen(word: WordEntity) {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, DetailFragment.newInstance(word))
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.searchFab.show()
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
        model.onGetInputWord(word)
    }

     private fun onItemClicked(word: WordEntity) {
         model.onRecycleItemClicked(word)
     }
}