package com.example.lightdictionary.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lightdictionary.R
import com.example.lightdictionary.data.WordEntity
import com.example.lightdictionary.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)
    private val adapter: DetailAdapter by lazy { DetailAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wordEntity: WordEntity =
            arguments?.getParcelable(ARG_WORD_PARAM) ?: WordEntity("", emptyList())

        binding.detailWordTextView.text = wordEntity.text

        binding.detailMeaningsRecycleView.adapter = adapter
        binding.detailMeaningsRecycleView.layoutManager = LinearLayoutManager(requireContext())
        adapter.updateList(wordEntity.meanings)

        binding.detailSwipeRefreshLayout.setOnRefreshListener { refreshRecyclerAdapter(wordEntity) }
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
        if (binding.detailSwipeRefreshLayout.isRefreshing) {
            binding.detailSwipeRefreshLayout.isRefreshing = false
        }
    }
}