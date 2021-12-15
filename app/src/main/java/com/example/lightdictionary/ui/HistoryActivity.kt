package com.example.lightdictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lightdictionary.R
import com.example.model.LoadWordsState
import com.example.model.WordEntity
import com.example.lightdictionary.databinding.ActivityHistoryBinding
import com.example.lightdictionary.presenter.history.HistoryController
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity(), HistoryController.View {
    private val binding: ActivityHistoryBinding by viewBinding(ActivityHistoryBinding::bind)
    private val model: HistoryController.BaseViewModel by viewModel()
    private val adapter: MainAdapter by lazy { MainAdapter(::onItemClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        model.loadStateLiveData.observe(this) { renderLoadState(it) }
        model.detailLiveData.observe(this) { renderDetailData(it) }

        binding.historyWordsRecyclerView .adapter = adapter
        binding.historyWordsRecyclerView.layoutManager = LinearLayoutManager(this)

        model.onViewCreated()
    }

    override fun renderDetailData(word: WordEntity?) {
        if (word != null) {
            showDetailScreen(word)
            model.onDetailScreenOpened()
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

    private fun showDetailScreen(word: WordEntity) {
        supportFragmentManager.beginTransaction()
            .add(R.id.history_container, DetailFragment.newInstance(word))
            .addToBackStack(null)
            .commit()
    }

    private fun showError(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun showWords(words: List<WordEntity>) {
        adapter.updateList(words)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.historyProgressLayout.visibility = when (isLoading) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    private fun onItemClicked(word: WordEntity) {
        model.onRecycleItemClicked(word)
    }
}