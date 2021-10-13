package com.app.pranavfreshworks.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Image loader class which will use Glide to load images.
 */
object ImageLoader {
    /**
     * Loads the image from url into ImageView.
     */
    fun loadImage(
        context: Context,
        imageView: ImageView,
        imageUrl: String?
    ) {
        Glide.with(context).asGif().load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions())
            .into(imageView)
    }
}