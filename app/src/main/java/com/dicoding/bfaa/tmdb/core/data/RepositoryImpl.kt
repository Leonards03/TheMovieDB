package com.dicoding.bfaa.tmdb.core.data

import com.dicoding.bfaa.tmdb.core.data.mapper.mapToDomain
import com.dicoding.bfaa.tmdb.core.data.mapper.mapToEntity
import com.dicoding.bfaa.tmdb.core.data.source.local.LocalDataSource
import com.dicoding.bfaa.tmdb.core.data.source.local.entity.MovieEntity
import com.dicoding.bfaa.tmdb.core.data.source.remote.RemoteDataSource
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.MovieResponse
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.Response
import com.dicoding.bfaa.tmdb.core.data.source.remote.response.TvShowResponse
import com.dicoding.bfaa.tmdb.core.di.IoDispatcher
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : Repository {
    override fun fetchDiscoverMovieList() =
        object : LoadResource<List<Movie>, List<MovieResponse>>(ioDispatcher) {
            override suspend fun createCall(): Flow<Response<List<MovieResponse>>> =
                remoteDataSource.getDiscoverMovieList()

            override fun mapRequestToResult(data: List<MovieResponse>): List<Movie> =
                data.mapToDomain()
        }.asFlow()

    override fun fetchNowPlayingMovies() =
        object : LoadResource<List<Movie>, List<MovieResponse>>(ioDispatcher) {
            override suspend fun createCall(): Flow<Response<List<MovieResponse>>> =
                remoteDataSource.getNowPlayingMovies()

            override fun mapRequestToResult(data: List<MovieResponse>): List<Movie> =
                data.mapToDomain()
        }.asFlow()

    override fun fetchUpcomingMovies() =
        object : LoadResource<List<Movie>, List<MovieResponse>>(ioDispatcher) {
            override suspend fun createCall(): Flow<Response<List<MovieResponse>>> =
                remoteDataSource.getUpcomingMovies()

            override fun mapRequestToResult(data: List<MovieResponse>): List<Movie> =
                data.mapToDomain()
        }.asFlow()

    override fun fetchPopularMovies() =
        object : LoadResource<List<Movie>, List<MovieResponse>>(ioDispatcher) {
            override suspend fun createCall(): Flow<Response<List<MovieResponse>>> =
                remoteDataSource.getPopularMovies()

            override fun mapRequestToResult(data: List<MovieResponse>): List<Movie> =
                data.mapToDomain()
        }.asFlow()

    override fun fetchTopRatedMovie() =
        object : LoadResource<List<Movie>,List<MovieEntity>, List<MovieResponse>>(ioDispatcher) {
            override suspend fun createCall(): Flow<Response<List<MovieResponse>>> =
                remoteDataSource.getTopRatedMovies()

            override fun mapRequestToResult(data: List<MovieResponse>): List<Movie> =
                data.mapToDomain()
        }.asFlow()

    override fun fetchMovie(id: Int) =
        object : LoadResource<Movie, MovieResponse>(ioDispatcher) {
            override fun shouldFetch(data: Movie?): Boolean =
                data == null

            override suspend fun loadFromDB(): Flow<Movie?> =
                localDataSource.getFavoriteMovie(id)

            override suspend fun createCall(): Flow<Response<MovieResponse>> =
                remoteDataSource.getMovie(id)

            override fun mapRequestToResult(data: MovieResponse): Movie =
                data.mapToDomain()
        }.asFlow()

    override fun fetchDiscoverTvList() =
        object : LoadResource<List<TvShow>, List<TvShowResponse>>(ioDispatcher) {
            override suspend fun createCall(): Flow<Response<List<TvShowResponse>>> =
                remoteDataSource.getDiscoverTvList()

            override fun mapRequestToResult(data: List<TvShowResponse>): List<TvShow> =
                data.mapToDomain()
        }.asFlow()

    override fun fetchAiringToday() =
        object : LoadResource<List<TvShow>, List<TvShowResponse>>(ioDispatcher) {
            override suspend fun createCall(): Flow<Response<List<TvShowResponse>>> =
                remoteDataSource.getAiringToday()
                    .flowOn(ioDispatcher)

            override fun mapRequestToResult(data: List<TvShowResponse>): List<TvShow> =
                data.mapToDomain()
        }.asFlow()

    override fun fetchLatestTvShow() =
        object : LoadResource<List<TvShow>, List<TvShowResponse>>(ioDispatcher) {
            override suspend fun createCall(): Flow<Response<List<TvShowResponse>>> =
                remoteDataSource.getLatestTvShow()

            override fun mapRequestToResult(data: List<TvShowResponse>): List<TvShow> =
                data.mapToDomain()
        }.asFlow()

    override fun fetchPopularTvShow() =
        object : LoadResource<List<TvShow>, List<TvShowResponse>>(ioDispatcher) {
            override suspend fun createCall(): Flow<Response<List<TvShowResponse>>> =
                remoteDataSource.getPopularTvShow()

            override fun mapRequestToResult(data: List<TvShowResponse>): List<TvShow> =
                data.mapToDomain()
        }.asFlow()

    override fun fetchTvShow(id: Int) =
        object : LoadResource<TvShow, TvShowResponse>(ioDispatcher) {
            override fun shouldFetch(data: TvShow?): Boolean =
                data == null

//            override suspend fun loadFromDB(): Flow<TvShow>? =
//                localDataSource.getFavoriteTvShow(id).map { tvShow -> tvShow. }

            override suspend fun createCall(): Flow<Response<TvShowResponse>> =
                remoteDataSource.getTvShow(id)

            override fun mapRequestToResult(data: TvShowResponse): TvShow =
                data.mapToDomain()
        }.asFlow()

    override fun getFavoriteMovies() =
        localDataSource.getFavoriteMovies()
            .distinctUntilChanged()
            .flowOn(ioDispatcher)
            .map { it.mapToDomain() }
//        var pagingSource: MoviePagingSource = MoviePagingSource(emptyList())
//        CoroutineScope(ioDispatcher).launch {
//            localDataSource.getFavoriteMovies()
//                .distinctUntilChanged()
//                .flowOn(ioDispatcher)
//                .map { it.mapToDomain() }
//                .collectLatest {
//                    pagingSource = MoviePagingSource(it)
//                }
//        }

    fun getFavoriteTvShows() = localDataSource.getFavoriteTvShows().map { it.mapToDomain() }

    override fun setFavorite(movie: Movie, itemIsFavorite: Boolean) {
        CoroutineScope(ioDispatcher).launch {
            val entity = movie.mapToEntity()
            if (itemIsFavorite) {
                localDataSource.addToFavorite(entity)
            } else {
                localDataSource.removeFromFavorite(entity)
            }
        }
    }
}