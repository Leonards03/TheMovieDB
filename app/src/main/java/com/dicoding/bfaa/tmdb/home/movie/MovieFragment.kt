package com.dicoding.bfaa.tmdb.home.movie

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.tmdb.core.data.states.ItemType
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.extension.invisible
import com.dicoding.bfaa.tmdb.core.extension.visible
import com.dicoding.bfaa.tmdb.core.presentation.adapter.MoviePagedAdapter
import com.dicoding.bfaa.tmdb.core.utils.EspressoIdlingResource
import com.dicoding.bfaa.tmdb.databinding.FragmentMovieBinding
import com.dicoding.bfaa.tmdb.detail.DetailActivity
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_TYPE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private val viewModel: MovieViewModel by viewModels()
    private var binding: FragmentMovieBinding? = null
    private val adapter = MoviePagedAdapter { movie -> intentToDetailsActivity(movie.id) }
    private var pagingJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
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
        loadMovies()
    }

    private fun setupRecyclerView() = binding?.apply {
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
        EspressoIdlingResource.increment()
        pagingJob?.cancel()
        pagingJob = viewLifecycleOwner.lifecycleScope.launch {
            setLoadingState(true)
            viewModel.moviesStream.collectLatest(::renderMovies)
        }
    }

    private suspend fun renderMovies(pagingData: PagingData<Movie>) {
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

    private fun intentToDetailsActivity(id: Int) =
        Intent(requireActivity(), DetailActivity::class.java).run {
            putExtra(EXTRA_ID, id)
            putExtra(EXTRA_TYPE, ItemType.Movie)
            startActivity(this)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}