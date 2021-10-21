package com.leonards.tmdb.app.home.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.domain.model.TvShow
import com.leonards.tmdb.core.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val searchUseCase: SearchUseCase,
) : ViewModel() {
    val query = MutableStateFlow(String())

    init {
        handle.get<String>(EXTRA_QUERY)?.let {
            query.value = it
            Timber.d(it)
        }
    }

    var movieSearchResult: Flow<PagingData<Movie>>? = null

    fun searchMovie() {
        val newResult = searchUseCase.searchMovie(query.value)
            .cachedIn(viewModelScope)
        movieSearchResult = newResult
    }

    var tvSearchResult: Flow<PagingData<TvShow>>? = null

    fun searchTvShow(){
        val newResult = searchUseCase.searchTvShow(query.value)
            .cachedIn(viewModelScope)
        tvSearchResult = newResult
    }

    companion object {
        const val EXTRA_QUERY = "extra_query"
    }
}