package com.dicoding.tmdb.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.dicoding.tmdb.core.data.Constants.DEFAULT_PAGE_SIZE
import com.dicoding.tmdb.core.data.mapper.mapToDomain
import com.dicoding.tmdb.core.data.mapper.mapToEntity
import com.dicoding.tmdb.core.data.source.local.LocalDataSource
import com.dicoding.tmdb.core.data.source.remote.RemoteDataSource
import com.dicoding.tmdb.core.data.source.remote.response.MovieResponse
import com.dicoding.tmdb.core.data.source.remote.response.Response
import com.dicoding.tmdb.core.data.source.remote.response.TvShowResponse
import com.dicoding.tmdb.core.data.states.LoadResource
import com.dicoding.tmdb.core.di.IoDispatcher
import com.dicoding.tmdb.core.domain.model.Movie
import com.dicoding.tmdb.core.domain.model.TvShow
import com.dicoding.tmdb.core.domain.repository.Repository
import com.dicoding.tmdb.core.presentation.paging.MoviePagingSource
import com.dicoding.tmdb.core.presentation.paging.TvShowPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) : Repository {
    override fun fetchMovies() = Pager(
        config = DEFAULT_PAGING_CONFIG,
        pagingSourceFactory = { MoviePagingSource(remoteDataSource) }
    ).flow

    override fun fetchTvShows() = Pager(
        config = DEFAULT_PAGING_CONFIG,
        pagingSourceFactory = { TvShowPagingSource(remoteDataSource) }
    ).flow

    override fun searchMovie(query: String) = Pager(
        config = DEFAULT_PAGING_CONFIG,
        pagingSourceFactory = { MoviePagingSource(remoteDataSource, query) }
    ).flow

    override fun searchTvShow(query: String) = Pager(
        config = DEFAULT_PAGING_CONFIG,
        pagingSourceFactory = { TvShowPagingSource(remoteDataSource, query) }
    ).flow

    override fun fetchMovie(id: Int) =
        object : LoadResource<Movie, MovieResponse>(dispatcher) {
            override fun shouldFetch(data: Movie?): Boolean =
                data == null

            override suspend fun loadFromDB(): Flow<Movie?> =
                localDataSource.getFavoriteMovie(id)

            override suspend fun createCall(): Flow<Response<MovieResponse>> =
                remoteDataSource.getMovie(id)

            override fun mapRequestToResult(data: MovieResponse): Movie =
                data.mapToDomain()
        }.flow

    override fun fetchTvShow(id: Int) =
        object : LoadResource<TvShow, TvShowResponse>(dispatcher) {
            override fun shouldFetch(data: TvShow?): Boolean =
                data == null

            override suspend fun loadFromDB(): Flow<TvShow?> =
                localDataSource.getFavoriteTvShow(id)

            override suspend fun createCall(): Flow<Response<TvShowResponse>> =
                remoteDataSource.getTvShow(id)

            override fun mapRequestToResult(data: TvShowResponse): TvShow =
                data.mapToDomain()
        }.flow

    override fun getFavoriteMovies() =
        localDataSource.getFavoriteMovies()
            .distinctUntilChanged()
            .flowOn(dispatcher)
            .map { it.mapToDomain() }

    override fun getFavoriteTvShows() =
        localDataSource.getFavoriteTvShows()
            .distinctUntilChanged()
            .flowOn(dispatcher)
            .map { it.mapToDomain() }

    override fun setFavorite(movie: Movie, itemIsFavorite: Boolean) {
        CoroutineScope(dispatcher).launch {
            val entity = movie.mapToEntity()
            if (itemIsFavorite) {
                localDataSource.addToFavorite(entity)
            } else {
                localDataSource.removeFromFavorite(entity)
            }
        }
    }

    override fun setFavorite(tvShow: TvShow, itemIsFavorite: Boolean) {
        CoroutineScope(dispatcher).launch {
            val entity = tvShow.mapToEntity()
            if (itemIsFavorite) {
                localDataSource.addToFavorite(entity)
            } else {
                localDataSource.removeFromFavorite(entity)
            }
        }
    }

    companion object {
        val DEFAULT_PAGING_CONFIG = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = DEFAULT_PAGE_SIZE
        )
    }
}