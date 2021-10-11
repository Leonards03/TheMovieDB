package com.leonards.tmdb.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leonards.tmdb.core.databinding.ItemRowLayoutBinding
import com.leonards.tmdb.core.data.states.ItemType
import com.leonards.tmdb.core.domain.model.DomainModel
import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.domain.model.TvShow
import com.leonards.tmdb.core.presentation.viewholder.CardViewHolder
import com.leonards.tmdb.core.utils.ImageSize

class FavoriteAdapter<T : DomainModel>(
    private val imageSize: ImageSize = ImageSize.Medium,
    private val onItemClick: (item: T) -> Unit,
) : RecyclerView.Adapter<CardViewHolder>() {
    private val list = ArrayList<T>()

    fun submitData(newList: List<T>) {
        with(list) {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder =
        CardViewHolder(
            ItemRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is Movie -> ItemType.Movie.ordinal
        is TvShow -> ItemType.TvShow.ordinal
        else -> throw IllegalArgumentException("Undefined data type ")
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) =
        when (holder.itemViewType) {
            ItemType.Movie.ordinal -> {
                val item = list[position] as Movie
                val callback = onItemClick as (item: Movie) -> Unit
                holder.bind(item, imageSize, callback)
            }
            ItemType.TvShow.ordinal -> {
                val item = list[position] as TvShow
                val callback = onItemClick as (item: TvShow) -> Unit
                holder.bind(item, imageSize, callback)
            }
            else -> throw IllegalArgumentException("Undefined itemType")
        }

    override fun getItemCount(): Int = list.size
}