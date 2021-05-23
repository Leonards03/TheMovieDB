package com.dicoding.bfaa.tmdb.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bfaa.tmdb.core.data.Resource
import com.dicoding.bfaa.tmdb.core.data.Resource.*
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.extension.glideImageWithOptions
import com.dicoding.bfaa.tmdb.core.extension.showSnackbar
import com.dicoding.bfaa.tmdb.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val content by lazy {
        binding.content
    }
    private val viewModel: DetailViewModel by viewModels()

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.movieDetails.observe(this, ::observeMovieDetails)
    }

    private fun observeMovieDetails(resource: Resource<Movie>) = when (resource) {
        is Error -> {
            Timber.e(resource.message)
        }
        is Loading -> {

        }
        is Success -> {
            resource.data.let { movie ->
                binding.imgCover.glideImageWithOptions(movie.poster)
                content.tvTitle.text = movie.title
                content.tvOverview.text = movie.overview
                isFavorite = resource.source == "DB"
                content.btnFavorite.setOnClickListener {
                    isFavorite = !isFavorite
                    viewModel.setFavorite(movie, isFavorite)
                    binding.root.showSnackbar("$isFavorite ${resource.source}")
                }

            }
        }
    }
//
//    private fun observeTvShowDetails(resource: Resource<Movie>) = when (resource) {
//        is Success -> {
//            resource.data?.let { tvShow ->
//                content.tvTitle.text = tvShow.title
//                Timber.d(tvShow.toString())
//            }
//        }
//        is Loading -> {
//        }
//        is Error -> handleError(resource.message)
//    }

    private fun handleError(message: String?) = Timber.e(message)


    private fun setLoadingState(isLoading: Boolean) = if (isLoading) {

    } else {

    }

    companion object {
        const val EXTRA_ID = "extra_item_id"
        const val EXTRA_DATA_IS_MOVIE = "extra_data_is_movie"
        const val NO_ID = -1
    }
}