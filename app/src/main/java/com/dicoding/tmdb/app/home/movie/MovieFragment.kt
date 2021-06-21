package com.dicoding.tmdb.app.home.movie

import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tmdb.app.R
import com.dicoding.tmdb.app.databinding.FragmentMovieBinding
import com.dicoding.tmdb.app.detail.DetailActivity
import com.dicoding.tmdb.app.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.tmdb.app.detail.DetailViewModel.Companion.EXTRA_TYPE
import com.dicoding.tmdb.app.home.search.SearchViewModel.Companion.EXTRA_QUERY
import com.dicoding.tmdb.app.home.search.movie.SearchMovieActivity
import com.dicoding.tmdb.app.utils.AppPreferences
import com.dicoding.tmdb.core.data.states.ItemType
import com.dicoding.tmdb.core.domain.model.Movie
import com.dicoding.tmdb.core.extension.invisible
import com.dicoding.tmdb.core.extension.showSnackbar
import com.dicoding.tmdb.core.extension.visible
import com.dicoding.tmdb.core.presentation.adapter.MoviePagedAdapter
import com.dicoding.tmdb.core.utils.ImageSize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment(), SearchView.OnQueryTextListener {
    private val viewModel: MovieViewModel by viewModels()
    private var binding: FragmentMovieBinding? = null
    private var pagingJob: Job? = null
    private lateinit var adapter: MoviePagedAdapter

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupRecyclerView(appPreferences.getImageSize())
            loadMovies()
        }
    }

    private fun setupRecyclerView(imageSize: ImageSize) = binding?.apply {
        adapter = MoviePagedAdapter(imageSize, ::intentToDetailsActivity)
        rvDiscoverMovie.apply {
            layoutManager = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
            adapter = this@MovieFragment.adapter
        }
    }

    /**
     * Setup the required observer on this Fragment
     */
    private fun loadMovies() {
        pagingJob = viewLifecycleOwner.lifecycleScope.launch {
            setLoadingState(true)
            viewModel.moviesStream.collectLatest(::renderMovies)
            adapter.loadStateFlow.collectLatest { loadState ->
                setLoadingState(loadState.refresh is LoadState.Loading)
            }
        }
    }

    private suspend fun renderMovies(pagingData: PagingData<Movie>) {
        setLoadingState(false)
        adapter.submitData(pagingData)
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

    private fun intentToDetailsActivity(movie: Movie) =
        Intent(requireActivity(), DetailActivity::class.java).run {
            putExtra(EXTRA_ID, movie.id)
            putExtra(EXTRA_TYPE, ItemType.Movie)
            startActivity(this)
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchManager = requireActivity().getSystemService<SearchManager>()
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        with(searchView) {
            setSearchableInfo(searchManager?.getSearchableInfo(requireActivity().componentName))
            queryHint = getString(R.string.hint_search_movie)
            setOnQueryTextListener(this@MovieFragment)
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
                Intent(requireActivity(), SearchMovieActivity::class.java).apply {
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