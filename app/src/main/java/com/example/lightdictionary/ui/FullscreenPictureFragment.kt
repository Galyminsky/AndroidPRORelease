package com.example.lightdictionary.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.lightdictionary.R
import com.example.lightdictionary.utils.viewById

class FullscreenPictureFragment : Fragment(R.layout.fragment_fullscreen_picture) {
    private val fullScreenImageView by viewById<AppCompatImageView>(R.id.full_screen_image_view)

    companion object {
        private const val BUNDLE_EXTRA_KEY = "PICTURE_BUNDLE_EXTRA_KEY"

        fun newInstance(pictureUrl: String) =
            FullscreenPictureFragment().apply {
                arguments = Bundle().apply { putString(BUNDLE_EXTRA_KEY, pictureUrl) }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pictureUrl = arguments?.getString(BUNDLE_EXTRA_KEY)

        ImageLoader(requireContext()).enqueue(
            ImageRequest.Builder(requireContext())
                .data(pictureUrl)
                .target(
                    onStart = {},
                    onSuccess = { result ->
                        fullScreenImageView.setImageDrawable(result)
                    },
                    onError = {
                        fullScreenImageView.setImageResource(R.drawable.ic_image_not_supported)
                    }
                )
                .build()
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (requireParentFragment() as Controller).blurScreen()
        }
    }

    override fun onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (requireParentFragment() as Controller).unblurScreen()
        }
        super.onDestroy()
    }

    interface Controller {
        @RequiresApi(Build.VERSION_CODES.S)
        fun blurScreen()
        @RequiresApi(Build.VERSION_CODES.S)
        fun unblurScreen()
    }
}