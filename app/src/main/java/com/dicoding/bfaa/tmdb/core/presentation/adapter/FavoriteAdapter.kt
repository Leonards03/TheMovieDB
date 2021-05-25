package com.dicoding.bfaa.tmdb.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.presentation.viewholder.VerticalViewHolder
import com.dicoding.bfaa.tmdb.databinding.ItemMovieBinding
import com.dicoding.bfaa.tmdb.databinding.ItemRowMovieBinding

class FavoriteAdapter : RecyclerView.Adapter<VerticalViewHolder>() {
    private val list: ArrayList<Any> = ArrayList()

    fun submitMovieList(newList: List<Movie>){
//        val diffCallback = MovieListDiffCallback(list as List<Movie>, newList)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list.clear()
        list.addAll(newList)
//        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    fun submitTvShowList(list: List<TvShow>){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder =
        VerticalViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) =
        if(holder.itemViewType == DATA_MOVIE){
            holder.bind(list[position] as Movie)
        } else {
            holder.bind(list[position] as TvShow)
        }

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is Movie -> DATA_MOVIE
        is TvShow -> DATA_TV_SHOW
        else -> throw IllegalArgumentException("Undefined data type ")
    }

    override fun getItemCount(): Int = list.size

    companion object {
        private const val DATA_MOVIE = 100
        private const val DATA_TV_SHOW = 101
    }
}