package com.dicoding.bfaa.tmdb.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.domain.usecase.DetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val detailUseCase: DetailUseCase,
) : ViewModel() {
    private var id: MutableStateFlow<Int>
    var itemType: MutableStateFlow<Int> = MutableStateFlow(NO_TYPE)
    private var _itemIsFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val itemIsFavorite get() = _itemIsFavorite.asLiveData()

    init {
        val tempId = handle.get<Int>(EXTRA_ID) ?: NO_ID
        id = MutableStateFlow(tempId)
        itemType.value = handle.get<Int>(EXTRA_TYPE) ?: NO_TYPE
    }

    val movieDetails by lazy { detailUseCase.getMovieDetails(id.value).asLiveData() }

    val tvShowDetails by lazy { detailUseCase.getTvShowDetails(id.value).asLiveData() }

    fun setFavorite(movie: Movie) = detailUseCase.setFavorite(movie, _itemIsFavorite.value)

    fun setFavorite(tvShow: TvShow) = detailUseCase.setFavorite(tvShow, _itemIsFavorite.value)

    fun setFavoriteState(state: Boolean) {
        _itemIsFavorite.value = state
    }

    fun toggleFavoriteState() {
        _itemIsFavorite.value = !_itemIsFavorite.value
    }

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val MOVIE_TYPE = 1
        const val TV_SHOW_TYPE = 2
        const val EXTRA_ID = "extra_item_id"
        const val NO_TYPE = -1
        const val NO_ID = -1
    }
}