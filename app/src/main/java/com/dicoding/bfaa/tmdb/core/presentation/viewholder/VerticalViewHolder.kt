package com.dicoding.bfaa.tmdb.core.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.extension.glideImageWithOptions
import com.dicoding.bfaa.tmdb.databinding.ItemRowMovieBinding

class VerticalViewHolder(private val binding: ItemRowMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) = with(binding) {
        imgPoster.glideImageWithOptions(movie.poster)
        tvTitle.text = movie.title
    }


    fun bind(tvShow: TvShow) = with(binding) {

    }
}