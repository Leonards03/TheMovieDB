package com.dicoding.bfaa.tmdb.core.data

import com.dicoding.bfaa.tmdb.core.data.source.remote.response.Response
import com.dicoding.bfaa.tmdb.core.extension.messageOrToString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import timber.log.Timber

abstract class LoadResource<ResultType, RequestType>(
    coroutineDispatcher: CoroutineDispatcher,
) {
    private var result: Flow<Resource<ResultType
            >> = flow {
        emit(Resource.Loading())
//        val dbSource = loadFromDB().first()
        if (true) {
            when (val response = createCall().first()) {
                is Response.Success -> {
                    val result = mapRequestToResult(response.data)
                    emit(Resource.Success(result))
                }
                is Response.Empty -> {
                    emit(Resource.Error("The requested data is empty!"))
                }
                is Response.Error -> {
                    onFetchFailed(response.exception)
                    emit(Resource.Error(response.exception.messageOrToString()))
                }
            }
        } else {
            // TODO 1 Load data from database
            // TODO 2 CreateCall from network
            // TODO 3 Save & Update if data are updated a.k.a Zip/combine
            loadFromDB()
                .filterNotNull()
                .collect {
                    emit(Resource.Success(it, "DB"))
                }

        }
    }.flowOn(coroutineDispatcher)

    private fun onFetchFailed(exception: Exception) = Timber.e(exception)

    protected open suspend fun loadFromDB(): Flow<ResultType?> = flow { emit(null) }


    protected open fun onFetchEmpty() {}

    /**
     * shouldFetch -> define to load from DB or not, set true by default to avoid boilerplate
     */
    protected open fun shouldFetch(data: ResultType?): Boolean = data == null

    protected abstract suspend fun createCall(): Flow<Response<RequestType>>

    protected open fun saveCallResult(data: RequestType) {}

    protected abstract fun mapRequestToResult(data: RequestType): ResultType

    fun asFlow(): Flow<Resource<ResultType>> = result
}