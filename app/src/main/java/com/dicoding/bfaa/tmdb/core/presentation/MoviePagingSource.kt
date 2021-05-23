package com.dicoding.bfaa.tmdb.core.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class MoviePagingSource(
    private val movies: List<Movie>,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val nextPageNumber = params.key ?: 1
            return LoadResult.Page(
                data = movies,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}