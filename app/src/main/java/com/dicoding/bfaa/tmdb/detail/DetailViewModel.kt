package com.dicoding.bfaa.tmdb.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.domain.usecase.DetailUseCase
import com.dicoding.bfaa.tmdb.detail.DetailActivity.Companion.EXTRA_ID
import com.dicoding.bfaa.tmdb.detail.DetailActivity.Companion.NO_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val detailUseCase: DetailUseCase,
) : ViewModel() {

    private var id: MutableStateFlow<Int>

    init {
        val tempId = handle.get<Int>(EXTRA_ID) ?: NO_ID
        id = MutableStateFlow(tempId)
    }

    val movieDetails by lazy { detailUseCase.getMovieDetails(id.value).asLiveData() }

    val tvShowDetails by lazy { detailUseCase.getTvShowDetails(id.value).asLiveData() }

    fun setFavorite(movie: Movie, state: Boolean) = detailUseCase.setFavorite(movie, state)

    fun setFavorite(tvShow: TvShow, state: Boolean) = detailUseCase.setFavorite(tvShow, state)

    companion object {
        private const val MOVIE_ID = "movieId"
        private const val TV_SHOW_ID = "tvShowId"
    }
}