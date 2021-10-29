package com.leonards.tmdb.app.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.leonards.tmdb.app.R
import com.leonards.tmdb.app.databinding.ActivityDetailBinding
import com.leonards.tmdb.app.state.UiState
import com.leonards.tmdb.app.utils.AppPreferences
import com.leonards.tmdb.app.utils.Event
import com.leonards.tmdb.core.domain.model.DomainModel
import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.domain.model.TvShow
import com.leonards.tmdb.core.extension.glideImageWithOptions
import com.leonards.tmdb.core.extension.invisible
import com.leonards.tmdb.core.extension.showSnackbar
import com.leonards.tmdb.core.extension.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val content by lazy { binding.content }
    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        enableBackButton()
        observeState()
        lifecycleScope.launchWhenCreated {
            viewModel.intent.send(DetailIntent.FetchDetails)
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect { state ->
                    when (state) {
                        UiState.Idle -> {
                        }
                        UiState.Loading -> handleLoading(state is UiState.Loading)
                        is UiState.Error -> handleError(state.throwable)
                        is UiState.Success -> {
                            handleLoading(state is UiState.Loading)
                            when (state.data) {
                                is Movie -> renderMovieDetails(state.data)
                                is TvShow -> renderTvShowDetails(state.data)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeFavoriteState(title: String) {
        viewModel.favoriteState.observe(this, { itemIsFavorite ->
            val buttonText = if (itemIsFavorite)
                R.string.remove_from_my_list
            else
                R.string.add_to_my_list
            content.btnFavorite.text = getString(buttonText)
        })
        viewModel.snackBarText.observe(this, { event ->
            showSnackbar(event, title)
        })
    }

    private fun showSnackbar(event: Event<Int>, title: String) {
        val message = event.getContent() ?: return
        content.root.showSnackbar(getString(message, title))
    }

    private fun renderMovieDetails(movie: Movie) {
        observeFavoriteState(movie.title)
        bindToView(movie)
    }

    private fun renderTvShowDetails(tvShow: TvShow) {
        observeFavoriteState(tvShow.title)
        bindToView(tvShow)
    }

    private fun bindToView(movie: Movie) {
        val imageSize = appPreferences.getImageSize()
        val backdropUrl = movie.getBackdropUrl(imageSize)
        val posterUrl = movie.getPosterUrl(imageSize)
        bind(backdropUrl, posterUrl, movie)

        with(content) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            ratingBar.rating = movie.voteAverage.toFloat()
                .div(2)
            tvRating.text = movie.voteAverage.toString()
            tvRuntime.text = getString(R.string.runtime, movie.runtime)
            tvDirector.text = movie.director
            tvGenre.text = movie.genres
            tvReleaseDate.text = movie.releaseDate
        }
    }

    private fun bindToView(tvShow: TvShow) {
        val imageSize = appPreferences.getImageSize()
        val backdropUrl = tvShow.getBackdropUrl(imageSize)
        val posterUrl = tvShow.getPosterUrl(imageSize)
        bind(backdropUrl, posterUrl, tvShow)

        with(content) {
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
        }
    }


    private fun bind(backdropUrl: String, posterUrl: String, model: DomainModel) = with(binding) {
        imgCover.glideImageWithOptions(backdropUrl)
        with(content) {
            imgPoster.glideImageWithOptions(posterUrl)
            btnFavorite.setOnClickListener { viewModel.toggleFavoriteState(model) }
            btnFavorite.isEnabled = true
        }
    }

    private fun handleError(throwable: Throwable) = Timber.e(throwable)

    private fun handleLoading(isLoading: Boolean) = with(content) {
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