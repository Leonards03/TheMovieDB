package com.dicoding.bfaa.tmdb.utils

import androidx.paging.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

/**
 * Thanks to Stack overflow D:
 * I hope Dicoding update its Jetpack Course to support Paging v3 soon enough.
 * https://stackoverflow.com/questions/66503911/unit-testing-a-repository-with-paging-3-using-a-a-remote-mediator-and-paging-sou
 */

@ExperimentalCoroutinesApi
suspend fun <T : Any> PagingData<T>.collectDataForTest(coroutineDispatcher: CoroutineDispatcher): List<T> {
    val dcb = object : DifferCallback {
        override fun onChanged(position: Int, count: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }
    val items = mutableListOf<T>()
    val diff = object : PagingDataDiffer<T>(dcb,coroutineDispatcher) {
        override suspend fun presentNewList(
            previousList: NullPaddedList<T>,
            newList: NullPaddedList<T>,
            newCombinedLoadStates: CombinedLoadStates,
            lastAccessedIndex: Int,
            onListPresentable: () -> Unit,
        ): Int? {
            for (index in 0 until newList.size)
                items.add(newList.getFromStorage(index))
            onListPresentable()
            return null
        }
    }
    diff.collectFrom(this)
    return items
}