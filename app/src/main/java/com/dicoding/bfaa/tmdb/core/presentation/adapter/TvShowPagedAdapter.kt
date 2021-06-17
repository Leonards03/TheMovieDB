package com.dicoding.bfaa.tmdb.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.presentation.utils.TvShowDiffCallback
import com.dicoding.bfaa.tmdb.core.presentation.viewholder.CardViewHolder
import com.dicoding.bfaa.tmdb.databinding.ItemRowLayoutBinding

class TvShowPagedAdapter(
    private val onItemClick: (tvShow: TvShow) -> Unit,
) : PagingDataAdapter<TvShow, CardViewHolder>(tvShowDiffCallback) {
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        getItem(position)?.let { tvShow ->
            holder.bind(tvShow, onItemClick)
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
        private val tvShowDiffCallback: TvShowDiffCallback = TvShowDiffCallback()
    }
}