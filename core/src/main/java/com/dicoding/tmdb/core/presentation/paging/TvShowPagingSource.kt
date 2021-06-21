package com.dicoding.tmdb.core.presentation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.tmdb.core.data.Constants.DEFAULT_PAGE_INDEX
import com.dicoding.tmdb.core.data.mapper.mapToDomain
import com.dicoding.tmdb.core.data.source.remote.RemoteDataSource
import com.dicoding.tmdb.core.domain.model.TvShow
import retrofit2.HttpException
import java.io.IOException

class TvShowPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val query: String? = null,
) : PagingSource<Int, TvShow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val pageNumber = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = if (query == null)
                remoteDataSource.getTvShows(pageNumber)
            else
                remoteDataSource.searchTvShow(query, pageNumber)

            val result = response.results
                .mapToDomain()

            val nextKey =
                if (result.isEmpty() || response.totalResults < 20 || pageNumber == response.totalPages || response.totalPages == 0)
                    null
                else
                    pageNumber + 1


            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}