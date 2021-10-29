package com.leonards.tmdb.app.home.tvshow

import android.app.SearchManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonards.tmdb.app.R
import com.leonards.tmdb.app.databinding.FragmentTvShowBinding
import com.leonards.tmdb.app.extension.intentToDetailsActivity
import com.leonards.tmdb.app.state.UiState
import com.leonards.tmdb.app.utils.AppPreferences
import com.leonards.tmdb.core.domain.model.TvShow
import com.leonards.tmdb.core.extension.invisible
import com.leonards.tmdb.core.extension.showSnackbar
import com.leonards.tmdb.core.extension.visible
import com.leonards.tmdb.core.presentation.adapter.TvShowPagedAdapter
import com.leonards.tmdb.core.utils.ImageSize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TvShowFragment : Fragment(), SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    private val viewModel: TvShowViewModel by activityViewModels()

    // variable to assign binding onCreateView and release on DestroyView
    private var _binding: FragmentTvShowBinding? = null

    /**
     *   using double bang operator to assure variable is not null
     *   even though it is not recommended to use it,
     *   currently im using it to avoid leak.
     **/
    private val binding get() = _binding!!
    private var pagingJob: Job? = null

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupRecyclerView(appPreferences.getImageSize())
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collect { state ->
                        when (state) {
                            UiState.Idle -> {
                            }

                            UiState.Loading -> binding.stateLoading.visible()

                            is UiState.Error -> showError(state.throwable)

                            is UiState.Success -> renderTvShows(state.data)
                        }

                    }
                }
            }
        }
    }

    private fun setupRecyclerView(imageSize: ImageSize) = binding.apply {
        rvDiscoverTv.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(context, 2)
                } else {
                    LinearLayoutManager(context)
                }
            adapter = TvShowPagedAdapter(imageSize, ::intentToDetailsActivity)
        }
    }

    private suspend fun renderTvShows(pagingData: PagingData<TvShow>) {
        binding.stateLoading.invisible()
        pagingJob?.cancel()
        pagingJob = viewLifecycleOwner.lifecycleScope.launch {
            (binding.rvDiscoverTv.adapter as TvShowPagedAdapter)
                .submitData(pagingData)
        }
    }

    private fun showError(throwable: Throwable) {
        Timber.d(throwable)
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

        menu.findItem(R.id.search).setOnActionExpandListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { submittedText ->
            if (submittedText.isBlank() || submittedText.isEmpty()) {
                binding.root.showSnackbar(getString(R.string.message_query_null))
            } else {
                viewModel.query.value = query
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.intent.send(TvShowIntent.SearchTvShows)
                }
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    /**
     * ref https://github.com/android/architecture-components-samples/blob/main/ViewBindingSample/app/src/main/java/com/android/example/viewbindingsample/InflateFragment.kt
     */
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean = true

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        lifecycleScope.launch {
            viewModel.intent.send(TvShowIntent.FetchTvShows)
        }
        return true
    }
}