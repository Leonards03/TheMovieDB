package com.dicoding.tmdb.favorite.presentation.favorite.tvshow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tmdb.app.detail.DetailActivity
import com.dicoding.tmdb.app.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.tmdb.app.detail.DetailViewModel.Companion.EXTRA_TYPE
import com.dicoding.tmdb.app.di.FavoriteModuleDependencies
import com.dicoding.tmdb.app.utils.AppPreferences
import com.dicoding.tmdb.core.data.states.ItemType
import com.dicoding.tmdb.core.domain.model.TvShow
import com.dicoding.tmdb.core.extension.invisible
import com.dicoding.tmdb.core.extension.visible
import com.dicoding.tmdb.core.presentation.adapter.FavoriteAdapter
import com.dicoding.tmdb.favorite.databinding.FragmentFavoriteTvShowBinding
import com.dicoding.tmdb.favorite.di.DaggerFavoriteComponent
import com.dicoding.tmdb.favorite.presentation.factory.ViewModelFactory
import com.dicoding.tmdb.favorite.presentation.favorite.FavoriteViewModel
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class TvShowFragment : Fragment() {
    private var binding: FragmentFavoriteTvShowBinding? = null
    private lateinit var adapter: FavoriteAdapter<TvShow>

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var appPreferences: AppPreferences

    private val viewModel: FavoriteViewModel by activityViewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(EntryPointAccessors.fromApplication(
                context.applicationContext,
                FavoriteModuleDependencies::class.java
            ))
            .build()
            .inject(this)
    }

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
        adapter = FavoriteAdapter(appPreferences.getImageSize(), ::intentToDetailsActivity)
        rvFavoriteTvShow.setHasFixedSize(true)
        rvFavoriteTvShow.layoutManager = LinearLayoutManager(context)
        rvFavoriteTvShow.adapter = adapter
    }

    private fun loadFavoriteTvShow() {
        viewModel.favoriteTvShows.observe(viewLifecycleOwner, ::renderTvShows)
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

    private fun intentToDetailsActivity(tvShow: TvShow) =
        Intent(requireActivity(), DetailActivity::class.java).run {
            putExtra(EXTRA_ID, tvShow.id)
            putExtra(EXTRA_TYPE, ItemType.TvShow)
            startActivity(this)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}