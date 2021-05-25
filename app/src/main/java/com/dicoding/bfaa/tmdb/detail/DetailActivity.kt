package com.dicoding.bfaa.tmdb.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.bfaa.tmdb.core.data.states.Resource
import com.dicoding.bfaa.tmdb.core.data.states.Resource.*
import com.dicoding.bfaa.tmdb.core.data.states.Source
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.extension.glideImageWithOptions
import com.dicoding.bfaa.tmdb.core.extension.showSnackbar
import com.dicoding.bfaa.tmdb.databinding.ActivityDetailBinding
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.MOVIE_TYPE
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.TV_SHOW_TYPE
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val content by lazy { binding.content }
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupObserver()
    }

    private fun setupObserver() = with(viewModel){
        when (itemType.value) {
            MOVIE_TYPE -> movieDetails.observe(this@DetailActivity, ::observeMovieDetails)
            TV_SHOW_TYPE -> tvShowDetails.observe(this@DetailActivity, ::observeTvShowDetails)
        }
        itemIsFavorite.observe(this@DetailActivity, {
            binding.root.showSnackbar("${it}")
        })
    }

    private fun observeMovieDetails(resource: Resource<Movie>) = when (resource) {
        is Success -> {
            resource.data.let { movie ->
                viewModel.setFavoriteState(resource.dataFromDB())
                Timber.d(resource.source.toString())
                bindToView(movie)
            }
        }
        is Loading -> setLoadingState(true)
        is Error -> handleError(resource.message)
    }

    private fun observeTvShowDetails(resource: Resource<TvShow>) = when (resource) {
        is Success -> {
            resource.data.let { tvShow ->
                content.tvTitle.text = tvShow.title
                Timber.d(tvShow.toString())
                bindToView(tvShow)
            }
        }
        is Loading -> setLoadingState(true)
        is Error -> handleError(resource.message)
    }

    private fun bindToView(movie: Movie) {
        binding.imgCover.glideImageWithOptions(movie.poster)

        with(content){
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            btnFavorite.setOnClickListener {
                viewModel.toggleFavoriteState()
                viewModel.setFavorite(movie)
            }
        }

    }

    private fun bindToView(tvShow: TvShow) {
        binding.imgCover.glideImageWithOptions(tvShow.poster)
        content.tvTitle.text = tvShow.title
        content.tvOverview.text = tvShow.overview
        content.btnFavorite.setOnClickListener {
            viewModel.toggleFavoriteState()
            viewModel.setFavorite(tvShow)
        }
    }

    private fun handleError(message: String?) = Timber.e(message)

    private fun setLoadingState(isLoading: Boolean) =
        if (isLoading) {

        } else {

        }

}