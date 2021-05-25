package com.dicoding.bfaa.tmdb.core.domain.usecase

import com.dicoding.bfaa.tmdb.core.data.states.Resource
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface DetailUseCase {
    fun getMovieDetails(id: Int): Flow<Resource<Movie>>
    fun getTvShowDetails(id: Int): Flow<Resource<TvShow>>
    fun setFavorite(movie: Movie, state: Boolean)
    fun setFavorite(tvShow: TvShow, state: Boolean)
}