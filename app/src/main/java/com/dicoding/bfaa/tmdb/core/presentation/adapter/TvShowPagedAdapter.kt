package com.dicoding.bfaa.tmdb.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.presentation.utils.TvShowDiffCallback
import com.dicoding.bfaa.tmdb.core.presentation.viewholder.VerticalViewHolder
import com.dicoding.bfaa.tmdb.databinding.ItemMovieBinding
class TvShowPagedAdapter: PagingDataAdapter<TvShow, VerticalViewHolder>(tvShowDiffCallback) {
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
        private val tvShowDiffCallback: TvShowDiffCallback = TvShowDiffCallback()
    }
}