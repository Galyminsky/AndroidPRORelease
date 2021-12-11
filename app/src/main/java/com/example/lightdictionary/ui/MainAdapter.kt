package com.example.lightdictionary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lightdictionary.R
import com.example.lightdictionary.data.WordEntity
import com.example.lightdictionary.databinding.ItemWordMainBinding

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
    private val binding by viewBinding(ItemWordMainBinding::bind)

    fun bind(word: WordEntity, onListItemClick: (WordEntity) -> Unit) {
        binding.itemWordMainMeaningTextView.text = word.text

        val meanings: String = word.meanings.joinToString(
            separator = " / ",
            transform = { meaningsEntity -> meaningsEntity.translation.text }
        )
        binding.itemWordMainTranslationTextView.text = meanings
        binding.root.setOnClickListener { onListItemClick(word) }
    }
}