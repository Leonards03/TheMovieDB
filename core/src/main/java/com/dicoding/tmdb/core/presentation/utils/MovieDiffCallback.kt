package com.dicoding.tmdb.core.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.tmdb.core.domain.model.Movie

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}