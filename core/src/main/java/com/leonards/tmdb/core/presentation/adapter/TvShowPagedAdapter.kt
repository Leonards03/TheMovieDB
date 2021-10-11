package com.leonards.tmdb.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.leonards.made.core.databinding.ItemRowLayoutBinding
import com.leonards.tmdb.core.domain.model.TvShow
import com.leonards.tmdb.core.presentation.utils.TvShowDiffCallback
import com.leonards.tmdb.core.presentation.viewholder.CardViewHolder
import com.leonards.tmdb.core.utils.ImageSize

class TvShowPagedAdapter(
    private val imageSize: ImageSize,
    private val onItemClick: (tvShow: TvShow) -> Unit,
) : PagingDataAdapter<TvShow, CardViewHolder>(tvShowDiffCallback) {
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        getItem(position)?.let { tvShow ->
            holder.bind(tvShow, imageSize, onItemClick)
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