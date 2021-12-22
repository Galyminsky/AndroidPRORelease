package com.example.lightdictionary.ui

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lightdictionary.R
import com.example.model.WordEntity
import com.example.lightdictionary.utils.viewById

private const val BLUR_RADIUS_X = 20f
private const val BLUR_RADIUS_Y = 20f

class DetailFragment : Fragment(R.layout.fragment_detail), FullscreenPictureFragment.Controller {
    private val adapter: DetailAdapter by lazy { DetailAdapter(::onItemClicked) }
    private val wordEntity: WordEntity by lazy {
        arguments?.getParcelable(ARG_WORD_PARAM) ?: WordEntity("", emptyList())
    }

    private val detailMeaningsRecycleView by viewById<RecyclerView>(R.id.detail_meanings_recycle_view)
    private val detailSwipeRefreshLayout by viewById<SwipeRefreshLayout>(R.id.detail_swipe_refresh_layout)
    private val detailWordTextView by viewById<TextView>(R.id.detail_word_text_view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun onItemClicked(imageUrl: String) {
        openFullscreenFragment(imageUrl)
    }

    private fun openFullscreenFragment(url: String) {
        childFragmentManager
            .beginTransaction()
            .add(R.id.detail_container, FullscreenPictureFragment.newInstance(url))
            .addToBackStack(null)
            .commit()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun blurScreen() {
        detailSwipeRefreshLayout.setRenderEffect(
            RenderEffect.createBlurEffect(BLUR_RADIUS_X, BLUR_RADIUS_Y, Shader.TileMode.MIRROR)
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun unblurScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                when (requireActivity()) {
                    is HistoryActivity -> R.id.history_container
                    else -> R.id.main_container
                },
                newInstance(wordEntity)
            )
            .addToBackStack(null)
            .commit()
    }
}