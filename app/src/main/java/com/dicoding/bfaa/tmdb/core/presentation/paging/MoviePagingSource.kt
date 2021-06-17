package com.dicoding.bfaa.tmdb.core.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.bfaa.tmdb.core.data.Constants.DEFAULT_PAGE_INDEX
import com.dicoding.bfaa.tmdb.core.data.mapper.mapToDomain
import com.dicoding.bfaa.tmdb.core.data.source.remote.RemoteDataSource
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val remoteDataSource: RemoteDataSource,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageNumber = params.key ?: DEFAULT_PAGE_INDEX
        try {
            val response = remoteDataSource.getMovies(pageNumber)

            val result = response.results
                .mapToDomain()

            return LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = if (pageNumber == response.totalPages) null else pageNumber + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}