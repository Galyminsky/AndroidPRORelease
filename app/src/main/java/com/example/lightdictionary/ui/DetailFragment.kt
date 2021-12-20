package com.example.lightdictionary.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lightdictionary.R
import com.example.model.WordEntity
import com.example.lightdictionary.utils.viewById

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val adapter: DetailAdapter by lazy { DetailAdapter() }

    private val detailMeaningsRecycleView by viewById<RecyclerView>(R.id.detail_meanings_recycle_view)
    private val detailSwipeRefreshLayout by viewById<SwipeRefreshLayout>(R.id.detail_swipe_refresh_layout)
    private val detailWordTextView by viewById<TextView>(R.id.detail_word_text_view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wordEntity: WordEntity =
            arguments?.getParcelable(ARG_WORD_PARAM) ?: WordEntity("", emptyList())

        detailWordTextView.text = wordEntity.text

        detailMeaningsRecycleView.adapter = adapter
        detailMeaningsRecycleView.layoutManager = LinearLayoutManager(requireContext())
        adapter.updateList(wordEntity.meanings)

        detailSwipeRefreshLayout.setOnRefreshListener { refreshRecyclerAdapter(wordEntity) }
    }

    companion object {
        private const val ARG_WORD_PARAM = "word entity"

        fun newInstance(word: WordEntity) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_WORD_PARAM, word)
                }
            }
    }

    private fun refreshRecyclerAdapter(wordEntity: WordEntity) {
        adapter.updateList(wordEntity.meanings)
        if (detailSwipeRefreshLayout.isRefreshing) {
            detailSwipeRefreshLayout.isRefreshing = false
        }
    }
}