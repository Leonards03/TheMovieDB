package com.dicoding.bfaa.tmdb.core.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.extension.glideImageWithOptions
import com.dicoding.bfaa.tmdb.databinding.ItemRowLayoutBinding

class CardViewHolder(private val binding: ItemRowLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie, onItemClick: (movie: Movie) -> Unit) = with(binding) {
        tvTitle.text = movie.title
        imgPoster.glideImageWithOptions(movie.poster)
        cvItem.setOnClickListener { onItemClick(movie) }
        ratingBar.rating = movie.voteAverage.toFloat().div(2)
        tvRating.text = movie.voteAverage.toString()
        tvDate.text = movie.releaseDate
    }

    fun bind(tvShow: TvShow, onItemClick: (tvShow: TvShow) -> Unit) = with(binding) {
        tvTitle.text = tvShow.title
        imgPoster.glideImageWithOptions(tvShow.poster)
        cvItem.setOnClickListener { onItemClick(tvShow) }
        ratingBar.rating = tvShow.voteAverage.toFloat().div(2)
        tvRating.text = tvShow.voteAverage.toString()
        tvDate.text = tvShow.firstAirDate
    }
}