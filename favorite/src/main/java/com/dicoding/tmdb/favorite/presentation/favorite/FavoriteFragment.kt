package com.dicoding.tmdb.favorite.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.dicoding.tmdb.app.R
import com.dicoding.tmdb.favorite.databinding.FragmentFavoriteBinding
import com.dicoding.tmdb.favorite.presentation.adapter.FavoritePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {
    private var binding: FragmentFavoriteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            viewPager.adapter = FavoritePagerAdapter(requireActivity())
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.nav_movie,
            R.string.nav_tv_show
        )
    }
}