package com.dicoding.bfaa.tmdb.home.tvshow

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.tmdb.core.data.states.ItemType
import com.dicoding.bfaa.tmdb.core.data.states.Resource.*
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.extension.invisible
import com.dicoding.bfaa.tmdb.core.extension.visible
import com.dicoding.bfaa.tmdb.core.presentation.adapter.TvShowPagedAdapter
import com.dicoding.bfaa.tmdb.core.utils.EspressoIdlingResource
import com.dicoding.bfaa.tmdb.databinding.FragmentTvShowBinding
import com.dicoding.bfaa.tmdb.detail.DetailActivity
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_TYPE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TvShowFragment : Fragment() {
    private val viewModel: TvShowViewModel by viewModels()
    private var binding: FragmentTvShowBinding? = null
    private val adapter = TvShowPagedAdapter { tvShow -> intentToDetailsActivity(tvShow.id) }
    private var pagingJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            initView()
        }
    }

    private fun initView() {
        setupRecyclerView()
        loadTvShows()
    }

    private fun setupRecyclerView() = binding?.apply {
        rvDiscoverTv.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(context, 2)
                } else {
                    LinearLayoutManager(context)
                }
            adapter = this@TvShowFragment.adapter
        }
    }

    /**
     * Setup the required observer on this Fragment
     */
    private fun loadTvShows() {
        EspressoIdlingResource.increment()
        pagingJob?.cancel()
        pagingJob = viewLifecycleOwner.lifecycleScope.launch {
            setLoadingState(true)
            viewModel.tvShowsStream.collectLatest(::renderTvShows)
        }
    }

    private suspend fun renderTvShows(pagingData: PagingData<TvShow>) {
        binding?.apply {
            if (!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow)
                EspressoIdlingResource.decrement()
            setLoadingState(false)
            adapter.submitData(pagingData)
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding?.apply {
            if (isLoading) {
                Timber.d("loading")
                stateLoading.root.visible()
            } else {
                stateLoading.root.invisible()
            }
        }
    }

    /**
     * Function to bind data to the target RecyclerView
     */
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