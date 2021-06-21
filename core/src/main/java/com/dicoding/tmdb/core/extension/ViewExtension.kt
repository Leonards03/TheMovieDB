@file:Suppress("unused")

package com.dicoding.tmdb.core.extension

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.dicoding.made.core.R
import com.dicoding.tmdb.core.data.mapper.valueOrEmpty
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

/** makes visible a view. */
fun View.visible() {
    visibility = View.VISIBLE
}

/** makes view invinsible **/
fun View.invisible() {
    visibility = View.GONE
}


fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun ImageView.glideImageWithOptions(url: String?) {
    val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(2000L)
        .setBaseAlpha(0.7f)
        .setHighlightAlpha(0.6f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    val options = RequestOptions()
        .placeholder(shimmerDrawable)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    try {
        val loadUrl = valueOrEmpty(url)
        Glide
            .with(context)
            .setDefaultRequestOptions(options)
            .load(loadUrl)
            .error(R.drawable.ic_baseline_broken_image_24)
            .into(this)

    } catch (exception: Exception) {
        Timber.e(exception)
        exception.printStackTrace()
    }
}
