package com.example.lightdictionary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.lightdictionary.R
import com.example.lightdictionary.data.MeaningsEntity
import com.example.lightdictionary.databinding.ItemDetailMeaningBinding
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.example.lightdictionary.utils.concatWithRoundBrackets
import com.example.lightdictionary.utils.toUrl

class DetailAdapter : RecyclerView.Adapter<DetailViewHolder>() {
    private var list: List<MeaningsEntity> = emptyList()

    fun updateList(list: List<MeaningsEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DetailViewHolder(parent)
    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) = holder.bind(list[position])
    override fun getItemCount() = list.size
}

class DetailViewHolder(private val parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_detail_meaning, parent, false)
) {
    private val binding: ItemDetailMeaningBinding by viewBinding(ItemDetailMeaningBinding::bind)

    fun bind(meaning: MeaningsEntity) {
        binding.itemDetailTranslationTextView.text =
            meaning.translation.text.concatWithRoundBrackets(meaning.translation.note)
        binding.itemDetailTranscriptionTextView.text = meaning.transcription

        loadImage(meaning.imageUrl.toUrl())
        setupSoundButton(meaning.soundUrl.toUrl())
    }

    private fun loadImage(url: String) {
        ImageLoader(parent.context).enqueue(
            ImageRequest.Builder(parent.context)
                .data(url)
                .target(
                    onStart = {},
                    onSuccess = { result ->
                        binding.itemDetailImageView.setImageDrawable(result)
                    },
                    onError = {
                        binding.itemDetailImageView.setImageResource(R.drawable.ic_image_not_supported)
                    }
                )
                .build()
        )
    }

    private fun setupSoundButton(url: String) {
        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepare()
        }
        binding.itemDetailSoundButton.setOnClickListener { mediaPlayer.start() }
    }
}
