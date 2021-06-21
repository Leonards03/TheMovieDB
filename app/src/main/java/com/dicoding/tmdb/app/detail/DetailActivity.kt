package com.dicoding.tmdb.app.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.tmdb.app.R
import com.dicoding.tmdb.app.databinding.ActivityDetailBinding
import com.dicoding.tmdb.app.utils.AppPreferences
import com.dicoding.tmdb.core.data.Constants
import com.dicoding.tmdb.core.data.states.ItemType
import com.dicoding.tmdb.core.data.states.Resource
import com.dicoding.tmdb.core.data.states.Resource.*
import com.dicoding.tmdb.core.domain.model.Movie
import com.dicoding.tmdb.core.domain.model.TvShow
import com.dicoding.tmdb.core.extension.glideImageWithOptions
import com.dicoding.tmdb.core.extension.invisible
import com.dicoding.tmdb.core.extension.showSnackbar
import com.dicoding.tmdb.core.extension.visible
import com.dicoding.tmdb.core.utils.ImageSize
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val content by lazy { binding.content }
    private val viewModel: DetailViewModel by viewModels()

    private var firstInitFlag = true

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        Timber.d(appPreferences.getImageSize().toString())
        enableBackButton()
        setupObserver()
    }

    private fun setupObserver() = with(viewModel) {
        when (itemType.value) {
            ItemType.Movie ->
                movieDetails.observe(this@DetailActivity, ::renderMovieDetails)

            ItemType.TvShow ->
                tvShowDetails.observe(this@DetailActivity, ::renderTvShowDetails)
        }
        isLoading.observe(this@DetailActivity, ::onLoadingChanged)
    }

    private fun observeFavoriteState(title: String) {
        content.btnFavorite.isEnabled = true
        viewModel.itemIsFavorite.observe(this@DetailActivity, { itemIsFavorite ->
            val btnTextId =
                if (itemIsFavorite) R.string.remove_from_my_list else R.string.add_to_my_list
            val snackbarTextId =
                if (itemIsFavorite) R.string.added_to_my_list else R.string.removed_from_my_list

            content.btnFavorite.text = getString(btnTextId)
            if (firstInitFlag) {
                firstInitFlag = false
            } else {
                binding.root.showSnackbar(getString(snackbarTextId, title))
            }
        })
    }

    private fun renderMovieDetails(resource: Resource<Movie>) {
        when (resource) {
            is Success -> resource.data.let { movie ->
                viewModel.setFavoriteState(resource.dataFromDB())
                observeFavoriteState(movie.title)
                bindToView(movie)
            }
            is Loading -> viewModel.setLoadingState(true)
            is Error -> handleError(resource.exception)
        }
    }

    private fun renderTvShowDetails(resource: Resource<TvShow>) {
        when (resource) {
            is Success -> resource.data.let { tvShow ->
                viewModel.setFavoriteState(resource.dataFromDB())
                observeFavoriteState(tvShow.title)
                bindToView(tvShow)
            }
            is Loading -> viewModel.setLoadingState(true)
            is Error -> handleError(resource.exception)
        }
    }

    private fun bindToView(movie: Movie) {
        val imageSize = appPreferences.getImageSize()
        val backdropUrl = movie.getBackdropUrl(imageSize)
        binding.imgCover.glideImageWithOptions(backdropUrl)

        with(content) {
            val posterUrl = movie.getPosterUrl(imageSize)
            imgPoster.glideImageWithOptions(posterUrl)
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            ratingBar.rating = movie.voteAverage.toFloat()
                .div(2)
            tvRating.text = movie.voteAverage.toString()
            tvRuntime.text = getString(R.string.runtime, movie.runtime)
            tvDirector.text = movie.director
            tvGenre.text = movie.genres
            tvReleaseDate.text = movie.releaseDate
            btnFavorite.setOnClickListener {
                viewModel.toggleFavoriteState()
                viewModel.setFavorite(movie)
            }
        }
        viewModel.setLoadingState(false)
    }

    private fun bindToView(tvShow: TvShow) {
        val backdropUrl = tvShow.getBackdropUrl(ImageSize.Medium)
        binding.imgCover.glideImageWithOptions(backdropUrl)

        with(content) {
            val posterUrl = Constants.getPosterUrl(tvShow.poster, ImageSize.Medium)
            imgPoster.glideImageWithOptions(posterUrl)
            tvTitle.text = tvShow.title
            tvOverview.text = tvShow.overview

            tvGenre.text = tvShow.genres

            ratingBar.rating = tvShow.voteAverage.toFloat()
                .div(2)
            tvRating.text = tvShow.voteAverage.toString()

            tvRuntimeField.text = getString(R.string.season)
            tvRuntime.text = tvShow.numberOfSeasons.toString()

            // replacement for director field
            tvDirectorField.text = getString(R.string.status)
            tvDirector.text = tvShow.status

            // replacement for Release date
            tvReleaseDateField.text = getString(R.string.airing_date)
            tvReleaseDate.text =
                getString(R.string.airing_date_format, tvShow.firstAirDate, tvShow.lastAirDate)

            btnFavorite.setOnClickListener {
                viewModel.toggleFavoriteState()
                viewModel.setFavorite(tvShow)
            }
            viewModel.setLoadingState(false)
        }
    }

    private fun handleError(exception: Exception) = Timber.e(exception)

    private fun onLoadingChanged(isLoading: Boolean) = with(content) {
        if (isLoading) {
            details.invisible()
            shimmerContainer.startShimmer()
        } else {
            shimmerContainer.stopShimmer()
            shimmerContainer.invisible()
            details.visible()
        }
    }

    private fun enableBackButton() = supportActionBar?.apply {
        setDisplayHomeAsUpEnabled(true)
        setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}