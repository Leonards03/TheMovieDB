package com.dicoding.bfaa.tmdb.core.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.core.content.getSystemService
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.dicoding.bfaa.tmdb.core.data.Constants.getImageBaseUrl
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

/**
 * Check connectivity to the Internet
 */
fun Context.checkConnectivity(): Boolean {
    val connectivityManager = this.getSystemService<ConnectivityManager>()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager?.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        with(activeNetwork) {
            return when {
                // Check connection over Wifi
                hasTransport(TRANSPORT_WIFI) -> true
                // Check connection over Cellular
                hasTransport(TRANSPORT_CELLULAR) -> true
                // Check connection over Ethernet
                hasTransport(TRANSPORT_ETHERNET) -> true
                // Check connection over Bluetooth
                hasTransport(TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        }
    } else {
        return connectivityManager?.activeNetworkInfo?.isConnected ?: false
    }
}


fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}


fun ImageView.loadEclipseImage(url: String?) {
    val loader = CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 40f
        start()
    }

    val option = RequestOptions()
//        .error(R.drawable.)
        .placeholder(loader)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .circleCrop()

    try {
        Glide.with(context)
            .setDefaultRequestOptions(option)
            .load(url)
            .into(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
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
        Glide
            .with(context)
            .setDefaultRequestOptions(options)
            .load(getImageBaseUrl(url ?: ""))
            .into(this)

    } catch (exception: Exception) {
        Timber.e(exception)
        exception.printStackTrace()
    }
}