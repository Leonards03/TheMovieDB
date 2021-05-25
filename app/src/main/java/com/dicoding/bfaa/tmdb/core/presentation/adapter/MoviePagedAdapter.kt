package com.dicoding.bfaa.tmdb.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.presentation.utils.MovieDiffCallback
import com.dicoding.bfaa.tmdb.core.presentation.viewholder.VerticalViewHolder
import com.dicoding.bfaa.tmdb.databinding.ItemMovieBinding
import com.dicoding.bfaa.tmdb.databinding.ItemRowMovieBinding

class MoviePagedAdapter:
    PagingDataAdapter<Movie, VerticalViewHolder>(movieDiffUtil) {

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder =
        VerticalViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    companion object {
        private val movieDiffUtil: MovieDiffCallback = MovieDiffCallback()
    }
}