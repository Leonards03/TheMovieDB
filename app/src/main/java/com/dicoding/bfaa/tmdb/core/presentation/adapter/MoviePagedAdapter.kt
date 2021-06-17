package com.dicoding.bfaa.tmdb.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.presentation.utils.MovieDiffCallback
import com.dicoding.bfaa.tmdb.core.presentation.viewholder.CardViewHolder
import com.dicoding.bfaa.tmdb.databinding.ItemRowLayoutBinding

class MoviePagedAdapter(
    private val onItemClick: (movie: Movie) -> Unit,
) : PagingDataAdapter<Movie, CardViewHolder>(movieDiffUtil) {
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie, onItemClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(
            ItemRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    companion object {
        private val movieDiffUtil: MovieDiffCallback = MovieDiffCallback()
    }
}