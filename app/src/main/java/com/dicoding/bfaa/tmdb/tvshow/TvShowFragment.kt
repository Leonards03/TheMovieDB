package com.dicoding.bfaa.tmdb.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.tmdb.core.data.states.Resource
import com.dicoding.bfaa.tmdb.core.data.states.Resource.*
import com.dicoding.bfaa.tmdb.core.domain.model.TvShow
import com.dicoding.bfaa.tmdb.core.presentation.adapter.ItemAdapter
import com.dicoding.bfaa.tmdb.databinding.FragmentTvShowBinding
import com.dicoding.bfaa.tmdb.detail.DetailActivity
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_TYPE
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.TV_SHOW_TYPE
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TvShowFragment : Fragment() {
    private val viewModel: TvShowViewModel by viewModels()

    private val discoverTvAdapter: ItemAdapter by lazy { ItemAdapter() }
    private val airingTodayAdapter: ItemAdapter by lazy { ItemAdapter() }
    private val popularTvAdapter: ItemAdapter by lazy { ItemAdapter() }
    private val latestTvAdapter: ItemAdapter by lazy { ItemAdapter() }

    private var binding: FragmentTvShowBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupRecyclerView()
            setupObserver()
        }
    }

    private fun initHorizontalRecyclerView(recyclerView: RecyclerView, itemAdapter: ItemAdapter) =
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = itemAdapter
            itemAdapter.layoutOrientation = ItemAdapter.HORIZONTAL
            GravitySnapHelper(Gravity.CENTER)
                .attachToRecyclerView(recyclerView)
        }

    private fun setupRecyclerView() = binding?.apply {
        initHorizontalRecyclerView(rvNowAiring, airingTodayAdapter)

        initHorizontalRecyclerView(rvDiscoverTv, discoverTvAdapter)

        initHorizontalRecyclerView(rvPopularTv, popularTvAdapter)

        initHorizontalRecyclerView(rvLatestTv, latestTvAdapter)
    }

    /**
     * Setup the required observer on this Fragment
     */
    private fun setupObserver() = with(viewModel) {
        airingToday.observe(viewLifecycleOwner, ::observeAiringToday)
        discoverTv.observe(viewLifecycleOwner, ::observeDiscover)
        popularTvShow.observe(viewLifecycleOwner, ::observePopular)
        latestTvShow.observe(viewLifecycleOwner, ::observeLatest)
    }

    private fun observeAiringToday(resource: Resource<List<TvShow>>) = when (resource) {
        is Error -> errorHandling(resource.message)
        is Loading -> setLoadingState(true)
        is Success -> resource.data?.let { data -> bindRecyclerView(airingTodayAdapter, data) }
    }

    private fun observeDiscover(resource: Resource<List<TvShow>>) = when(resource){
        is Error -> errorHandling(resource.message)
        is Loading -> setLoadingState(true)
        is Success -> resource.data?.let { data -> bindRecyclerView(discoverTvAdapter, data) }
    }

    private fun observePopular(resource: Resource<List<TvShow>>) = when (resource){
        is Error -> errorHandling(resource.message)
        is Loading -> setLoadingState(true)
        is Success -> resource.data?.let { data -> bindRecyclerView(popularTvAdapter, data) }
    }

    private fun observeLatest(resource: Resource<List<TvShow>>) = when(resource){
        is Error -> errorHandling(resource.message)
        is Loading -> setLoadingState(true)
        is Success -> resource.data?.let { data -> bindRecyclerView(latestTvAdapter, data) }
    }

    private fun bindRecyclerView(adapter: ItemAdapter, data: List<TvShow>) = with(adapter) {
        Timber.d(data.toString())
        setLoadingState(false)
        submitItemList(data)
        notifyDataSetChanged()
    }

    private fun setLoadingState(isLoading: Boolean) = if (isLoading) {

    } else {

    }

    /**
     * Function to bind data to the target RecyclerView
     */
    private fun intentToDetailsActivity(id: Int) =
        Intent(requireActivity(), DetailActivity::class.java).run {
            putExtra(EXTRA_ID, id)
            putExtra(EXTRA_TYPE, TV_SHOW_TYPE)
            startActivity(this)
        }

    private fun errorHandling(message: String?) =
        Timber.e(message)

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}