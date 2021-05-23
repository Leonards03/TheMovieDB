package com.dicoding.bfaa.tmdb.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.dicoding.bfaa.tmdb.core.domain.usecase.FavoriteUseCase
import com.dicoding.bfaa.tmdb.core.presentation.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    favoriteUseCase: FavoriteUseCase,
) : ViewModel() {
//    val favoriteMovies = Pager(
//        config = PagingConfig(pageSize = 10, enablePlaceholders = true)
//    ) { MoviePagingSource(favoriteUseCase.getFavoriteMovies()) }
//        .flow
//        .map { pagingData ->
//            pagingData.map { data -> data }
//        }
//        .cachedIn(viewModelScope)

    val favoriteMovies = favoriteUseCase.getFavoriteMovies().asLiveData()
}