package com.leonards.tmdb.app.home.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonards.tmdb.core.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val searchUseCase: SearchUseCase,
) : ViewModel() {
    private val query = MutableStateFlow(String())

    init {
        handle.get<String>(EXTRA_QUERY)?.let {
            query.value = it
            Timber.d(it)
        }
    }

    val searchMovie by lazy {
        searchUseCase.searchMovie(query.value)
            .cachedIn(viewModelScope)
    }

    val searchTvShow by lazy {
        searchUseCase.searchTvShow(query.value)
            .cachedIn(viewModelScope)
    }

    companion object {
        const val EXTRA_QUERY = "extra_query"
    }
}