package com.example.lightdictionary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lightdictionary.R
import com.example.model.WordEntity
import com.example.lightdictionary.utils.viewById

class MainAdapter(private var onListItemClick: (WordEntity) -> Unit) : RecyclerView.Adapter<MainViewHolder>() {
    private var list: List<WordEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainViewHolder(parent)
    override fun getItemCount() = list.size
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position], onListItemClick)
    }

    fun updateList(list: List<WordEntity>) {
        this.list = list
        notifyDataSetChanged()
    }
}

class MainViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_word_main, parent, false)
) {
    private val itemWordMainMeaningTextView by viewById<TextView>(R.id.item_word_main_meaning_text_view)
    private val itemWordMainTranslationTextView by viewById<TextView>(R.id.item_word_main_translation_text_view)

    fun bind(word: WordEntity, onListItemClick: (WordEntity) -> Unit) {
        itemWordMainMeaningTextView.text = word.text

        val meanings: String = word.meanings.joinToString(
            separator = " / ",
            transform = { meaningsEntity -> meaningsEntity.translation.text }
        )
        itemWordMainTranslationTextView.text = meanings
        itemView.rootView.setOnClickListener { onListItemClick(word) }
    }
}