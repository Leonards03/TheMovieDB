package com.dicoding.tmdb.app.home.tvshow

import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tmdb.app.R
import com.dicoding.tmdb.app.databinding.FragmentTvShowBinding
import com.dicoding.tmdb.app.detail.DetailActivity
import com.dicoding.tmdb.app.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.tmdb.app.detail.DetailViewModel.Companion.EXTRA_TYPE
import com.dicoding.tmdb.app.home.search.SearchViewModel.Companion.EXTRA_QUERY
import com.dicoding.tmdb.app.home.search.tvshow.SearchTvShowActivity
import com.dicoding.tmdb.app.utils.AppPreferences
import com.dicoding.tmdb.core.data.states.ItemType
import com.dicoding.tmdb.core.domain.model.TvShow
import com.dicoding.tmdb.core.extension.invisible
import com.dicoding.tmdb.core.extension.showSnackbar
import com.dicoding.tmdb.core.extension.visible
import com.dicoding.tmdb.core.presentation.adapter.TvShowPagedAdapter
import com.dicoding.tmdb.core.utils.ImageSize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TvShowFragment : Fragment(), SearchView.OnQueryTextListener {
    private val viewModel: TvShowViewModel by viewModels()
    private var binding: FragmentTvShowBinding? = null
    private var pagingJob: Job? = null
    private lateinit var adapter: TvShowPagedAdapter

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupRecyclerView(appPreferences.getImageSize())
            loadTvShows()
        }
    }

    private fun setupRecyclerView(imageSize: ImageSize) = binding?.apply {
        adapter = TvShowPagedAdapter(imageSize, ::intentToDetailsActivity)
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
        pagingJob?.cancel()
        pagingJob = viewLifecycleOwner.lifecycleScope.launch {
            setLoadingState(true)
            viewModel.tvShowsStream.collectLatest(::renderTvShows)
        }
    }

    private suspend fun renderTvShows(pagingData: PagingData<TvShow>) {
        binding?.apply {
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
    private fun intentToDetailsActivity(tvShow: TvShow) =
        Intent(requireActivity(), DetailActivity::class.java).run {
            putExtra(EXTRA_ID, tvShow.id)
            putExtra(EXTRA_TYPE, ItemType.TvShow)
            startActivity(this)
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchManager = requireActivity().getSystemService<SearchManager>()
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager?.getSearchableInfo(requireActivity().componentName))
            queryHint = getString(R.string.hint_search_tv)
            setOnQueryTextListener(this@TvShowFragment)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { submittedText ->
            if (submittedText.isBlank() || submittedText.isEmpty()) {
                binding?.apply {
                    root.showSnackbar(getString(R.string.message_query_null))
                }
            } else {
                Intent(requireActivity(), SearchTvShowActivity::class.java).apply {
                    putExtra(EXTRA_QUERY, submittedText)
                    startActivity(this)
                }
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}