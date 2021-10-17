package com.zivapp.newsapplication.utils

import android.widget.ImageView
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.zivapp.newsapplication.R

fun loadWithCoil(imageView: ImageView, imageUrl: String?, circulate: Boolean = false) {
    imageView.load(imageUrl) {
        setupCoilSettings(imageView, circulate)
    }
}

private fun ImageRequest.Builder.setupCoilSettings(imageView: ImageView, circulate: Boolean) {
    if (circulate) transformations(CircleCropTransformation())

    val defaultImage = R.drawable.ic_image_placeholder
    placeholder(defaultImage)

    listener(
            onError = { _, _ ->
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.setImageResource(defaultImage)
            }
    )
}