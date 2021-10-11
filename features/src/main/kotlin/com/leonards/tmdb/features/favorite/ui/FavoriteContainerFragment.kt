package com.leonards.tmdb.features.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.leonards.tmdb.app.R
import com.leonards.tmdb.features.databinding.FragmentFavoriteContainerBinding
import com.leonards.tmdb.features.favorite.presentation.adapter.FavoritePagerAdapter

class FavoriteContainerFragment : Fragment() {
    private var _binding: FragmentFavoriteContainerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteContainerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.apply {
            viewPager.adapter = FavoritePagerAdapter(this@FavoriteContainerFragment)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = getString(TAB_TITLES[position])
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.nav_movie,
            R.string.nav_tv_show
        )
    }
}