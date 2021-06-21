package com.dicoding.tmdb.favorite.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.tmdb.favorite.presentation.favorite.movie.MovieFragment
import com.dicoding.tmdb.favorite.presentation.favorite.tvshow.TvShowFragment

class FavoritePagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MovieFragment()
            else -> TvShowFragment()
        }
}