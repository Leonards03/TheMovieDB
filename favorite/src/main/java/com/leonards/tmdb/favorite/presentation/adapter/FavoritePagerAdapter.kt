package com.leonards.tmdb.favorite.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.leonards.tmdb.favorite.ui.movie.MovieFragment
import com.leonards.tmdb.favorite.ui.tvshow.TvShowFragment

class FavoritePagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> MovieFragment()
            else -> TvShowFragment()
        }
}