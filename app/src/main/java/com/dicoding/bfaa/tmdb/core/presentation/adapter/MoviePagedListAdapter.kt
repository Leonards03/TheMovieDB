package com.dicoding.bfaa.tmdb.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.extension.glideImageWithOptions
import com.dicoding.bfaa.tmdb.core.presentation.utils.MovieDiffCallback
import com.dicoding.bfaa.tmdb.core.presentation.viewholder.VerticalViewHolder
import com.dicoding.bfaa.tmdb.databinding.ItemRowMovieBinding

class MoviePagedListAdapter:
    PagingDataAdapter<Movie, VerticalViewHolder>(movieDiffUtil) {

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder =
        VerticalViewHolder(
            ItemRowMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    companion object {
        private val movieDiffUtil: MovieDiffCallback = MovieDiffCallback()
    }
}