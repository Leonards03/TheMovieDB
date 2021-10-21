package com.leonards.tmdb.app.home.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.leonards.tmdb.core.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val searchUseCase: SearchUseCase,
) : ViewModel() {
    val query = MutableStateFlow(String())
    init {
        handle.get<String>(EXTRA_QUERY)?.let {
            query.value = it
        }
    }

    val movieSearchResult by lazy {
        query.flatMapLatest { searchUseCase.searchMovie(it) }
            .cachedIn(viewModelScope)
    }

    val tvSearchResult by lazy {
        query.flatMapLatest { searchUseCase.searchTvShow(it) }
            .cachedIn(viewModelScope)
    }

    companion object {
        const val EXTRA_QUERY = "extra_query"
    }
}