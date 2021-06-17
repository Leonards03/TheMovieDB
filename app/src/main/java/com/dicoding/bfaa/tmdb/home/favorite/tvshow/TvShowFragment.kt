package com.dicoding.bfaa.tmdb.home.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.tmdb.core.data.states.ItemType
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.extension.invisible
import com.dicoding.bfaa.tmdb.core.extension.visible
import com.dicoding.bfaa.tmdb.core.presentation.adapter.FavoriteAdapter
import com.dicoding.bfaa.tmdb.core.utils.EspressoIdlingResource
import com.dicoding.bfaa.tmdb.databinding.FragmentFavoriteTvShowBinding
import com.dicoding.bfaa.tmdb.detail.DetailActivity
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_TYPE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFragment : Fragment() {
    private var binding: FragmentFavoriteTvShowBinding? = null
    private val viewModel: TvShowViewModel by viewModels()
    private val adapter = FavoriteAdapter<TvShow> { intentToDetailsActivity(it.id) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupRecyclerView()
            loadFavoriteTvShow()
        }
    }

    private fun setupRecyclerView() = binding?.apply {
        rvFavoriteTvShow.setHasFixedSize(true)
        rvFavoriteTvShow.layoutManager = LinearLayoutManager(context)
        rvFavoriteTvShow.adapter = adapter
    }

    private fun loadFavoriteTvShow() {
        EspressoIdlingResource.increment()
        viewModel.favoriteTvShows.observe(viewLifecycleOwner, ::renderTvShows)
        if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow)
            EspressoIdlingResource.decrement()
    }

    private fun renderTvShows(list: List<TvShow>) {
        adapter.submitData(list)
        binding?.apply {
            if (list.isEmpty()) {
                stateEmpty.root.visible()
            } else {
                stateEmpty.root.invisible()
            }
        }
    }

    private fun intentToDetailsActivity(id: Int) =
        Intent(requireActivity(), DetailActivity::class.java).run {
            putExtra(EXTRA_ID, id)
            putExtra(EXTRA_TYPE, ItemType.TvShow)
            startActivity(this)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}