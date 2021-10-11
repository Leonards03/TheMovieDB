package com.leonards.tmdb.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.leonards.tmdb.core.databinding.ItemRowLayoutBinding
import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.presentation.utils.MovieDiffCallback
import com.leonards.tmdb.core.presentation.viewholder.CardViewHolder
import com.leonards.tmdb.core.utils.ImageSize

class MoviePagedAdapter(
    private val imageSize: ImageSize,
    private val onItemClick: (movie: Movie) -> Unit,
) : PagingDataAdapter<Movie, CardViewHolder>(movieDiffUtil) {
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bind(movie, imageSize, onItemClick)
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