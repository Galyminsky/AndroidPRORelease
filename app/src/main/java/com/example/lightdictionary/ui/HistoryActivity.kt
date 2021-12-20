package com.example.lightdictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lightdictionary.R
import com.example.model.LoadWordsState
import com.example.model.WordEntity
import com.example.lightdictionary.presenter.history.HistoryController
import com.example.lightdictionary.utils.viewById
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.scope.Scope

class HistoryActivity : AppCompatActivity(), HistoryController.View, KoinScopeComponent {
    private val adapter: MainAdapter by lazy { MainAdapter(::onItemClicked) }

    override val scope: Scope
        get() = getOrCreateScope().value
    private val model = scope.get<HistoryController.BaseViewModel>()

    private val historyWordsRecyclerView by viewById<RecyclerView>(R.id.history_words_recycler_view)
    private val historyProgressLayout by viewById<FrameLayout>(R.id.history_progress_layout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        model.loadStateLiveData.observe(this) { renderLoadState(it) }
        model.detailLiveData.observe(this) { renderDetailData(it) }

        historyWordsRecyclerView .adapter = adapter
        historyWordsRecyclerView.layoutManager = LinearLayoutManager(this)

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
        historyProgressLayout.visibility = when (isLoading) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    private fun onItemClicked(word: WordEntity) {
        model.onRecycleItemClicked(word)
    }

    override fun onDestroy() {
        scope.close()
        super.onDestroy()
    }
}