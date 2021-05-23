package com.dicoding.bfaa.tmdb.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.tmdb.core.data.Constants.IMAGE_BASE_URL
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.extension.glideImageWithOptions
import com.dicoding.bfaa.tmdb.core.presentation.viewholder.VerticalViewHolder
import com.dicoding.bfaa.tmdb.databinding.ItemMovieBinding
import com.dicoding.bfaa.tmdb.databinding.ItemRowMovieBinding
import com.dicoding.bfaa.tmdb.databinding.ItemTvShowBinding

class ItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list: ArrayList<Any> = ArrayList()
    private var onMovieClickCallback: OnMovieClickCallback? = null
    private var onTvShowClickCallback: OnTvShowClickCallback? = null
    var layoutOrientation = UNDEFINED

    fun setOnMovieClickCallback(onMovieClickCallback: OnMovieClickCallback) {
        this.onMovieClickCallback = onMovieClickCallback
    }

    fun setOnTvShowClickCallback(onTvShowClickCallback: OnTvShowClickCallback) {
        this.onTvShowClickCallback = onTvShowClickCallback
    }

    fun submitItemList(list: List<Any>) = with(this.list) {
        clear()
        addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder = when (layoutOrientation) {
         HORIZONTAL -> HorizontalViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        VERTICAL -> VerticalViewHolder(
            ItemRowMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        else -> throw IllegalArgumentException("Undefined view type")
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder.itemViewType) {
            DATA_MOVIE -> if (layoutOrientation == HORIZONTAL) {
                (holder as HorizontalViewHolder).bind(list[position] as Movie)
            } else {
                (holder as VerticalViewHolder).bind(list[position] as Movie)
            }
            DATA_TV_SHOW -> if (layoutOrientation == HORIZONTAL) {
                (holder as HorizontalViewHolder).bind(list[position] as TvShow)
            } else {
                (holder as VerticalViewHolder).bind(list[position] as TvShow)
            }
            else -> throw IllegalArgumentException("Undefined view type")
        }


    override fun getItemCount(): Int = list.size

    inner class HorizontalViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) = with(binding) {
            imgPoster.glideImageWithOptions("$IMAGE_BASE_URL${movie.poster}")
            cvMovie.setOnClickListener { onMovieClickCallback?.onItemClicked(movie) }
//            tvTitle.text = movie.title
        }

        fun bind(tvShow: TvShow) = with(binding) {
            imgPoster.glideImageWithOptions("$IMAGE_BASE_URL${tvShow.poster}")
            cvMovie.setOnClickListener { onTvShowClickCallback?.onItemClicked(tvShow) }
        }
    }

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is Movie -> DATA_MOVIE
        is TvShow -> DATA_TV_SHOW
        else -> throw IllegalArgumentException("Undefined view type")
    }


    interface OnMovieClickCallback {
        fun onItemClicked(data: Movie)
    }

    interface OnTvShowClickCallback {
        fun onItemClicked(data: TvShow)
    }

    companion object {
        const val UNDEFINED = -1
        const val HORIZONTAL = 1
        const val VERTICAL = 2
        private const val DATA_MOVIE = 100
        private const val DATA_TV_SHOW = 101
    }
}