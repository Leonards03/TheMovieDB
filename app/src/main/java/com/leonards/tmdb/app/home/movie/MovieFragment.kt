package com.leonards.tmdb.app.home.movie

import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonards.tmdb.app.R
import com.leonards.tmdb.app.databinding.FragmentMovieBinding
import com.leonards.tmdb.app.extension.intentToDetailsActivity
import com.leonards.tmdb.app.home.search.SearchViewModel.Companion.EXTRA_QUERY
import com.leonards.tmdb.app.home.search.movie.SearchMovieActivity
import com.leonards.tmdb.app.utils.AppPreferences
import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.extension.invisible
import com.leonards.tmdb.core.extension.showSnackbar
import com.leonards.tmdb.core.extension.visible
import com.leonards.tmdb.core.presentation.adapter.MoviePagedAdapter
import com.leonards.tmdb.core.utils.ImageSize
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment(), SearchView.OnQueryTextListener {
    private val viewModel: MovieViewModel by activityViewModels()

    /**
     *  variable to assign binding onCreateView and release on DestroyView
     *  Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
     */
    private var _binding: FragmentMovieBinding? = null

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
        _binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupRecyclerView(appPreferences.getImageSize())
            loadMovies()
        }
    }

    private fun setupRecyclerView(imageSize: ImageSize) = binding.apply {
        rvDiscoverMovie.apply {
            layoutManager = if (resources.configuration.orientation == ORIENTATION_LANDSCAPE) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
            adapter = MoviePagedAdapter(imageSize, ::intentToDetailsActivity)
        }
    }

    /**
     * Setup the required observer on this Fragment
     */
    private fun loadMovies() {
        pagingJob?.cancel()
        pagingJob = viewLifecycleOwner.lifecycleScope.launch {
            setLoadingState(true)
            viewModel.moviesStream.collectLatest(::renderMovies)
        }
    }

    private suspend fun renderMovies(pagingData: PagingData<Movie>) {
        setLoadingState(false)
        (binding.rvDiscoverMovie.adapter as MoviePagedAdapter)
            .submitData(pagingData)
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                Timber.d("loading")
                stateLoading.root.visible()
            } else {
                stateLoading.root.invisible()
            }
        }
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
                binding.root.showSnackbar(getString(R.string.message_query_null))
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

    /**
     * ref https://github.com/android/architecture-components-samples/blob/main/ViewBindingSample/app/src/main/java/com/android/example/viewbindingsample/InflateFragment.kt
     */
    override fun onDestroyView() {
        pagingJob = null
        _binding = null
        super.onDestroyView()
    }

}