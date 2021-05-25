package com.dicoding.bfaa.tmdb.core.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.tmdb.core.data.Constants
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.extension.glideImageWithOptions
import com.dicoding.bfaa.tmdb.databinding.ItemMovieBinding

class CardViewHolder(private val binding: ItemMovieBinding, onItemClicked: () -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) = with(binding) {
        imgPoster.glideImageWithOptions("${Constants.IMAGE_BASE_URL}${movie.poster}")
//        cvMovie.setOnClickListener { onMovieClickCallba ck?.onItemClicked(movie) }
//            tvTitle.text = movie.title
    }

    fun bind(tvShow: TvShow) = with(binding) {
        imgPoster.glideImageWithOptions("${Constants.IMAGE_BASE_URL}${tvShow.poster}")
//        cvMovie.setOnClickListener { onTvShowClickCallback?.onItemClicked(tvShow) }
    }
}