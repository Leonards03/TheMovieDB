package com.dicoding.tmdb.favorite.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.tmdb.core.domain.usecase.FavoriteUseCase
import com.dicoding.tmdb.favorite.ui.FavoriteViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T  =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->{
                FavoriteViewModel(favoriteUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel Class: ${modelClass.name}")
        }
}