package com.leonards.tmdb.core.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.leonards.tmdb.core.domain.model.TvShow

class TvShowDiffCallback : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
        oldItem == newItem
}