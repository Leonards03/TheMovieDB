package com.leonards.tmdb.app.home.search.movie

import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonards.tmdb.app.R
import com.leonards.tmdb.app.databinding.ActivitySearchResultBinding
import com.leonards.tmdb.app.extension.toDetailsActivity
import com.leonards.tmdb.app.home.search.SearchViewModel
import com.leonards.tmdb.app.utils.AppPreferences
import com.leonards.tmdb.core.extension.showSnackbar
import com.leonards.tmdb.core.presentation.adapter.MoviePagedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SearchMovieActivity : AppCompatActivity(), SearchView.OnQueryTextListener{
    private val viewModel: SearchViewModel by viewModels()
    private val binding by lazy {
        ActivitySearchResultBinding.inflate(layoutInflater)
    }
    private var pagingJob: Job? = null
    private lateinit var adapter: MoviePagedAdapter

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initSupportBar()
        setupRecyclerView()
        loadMovies(null)
    }

    private fun setupRecyclerView() = with(binding) {
        adapter = MoviePagedAdapter(appPreferences.getImageSize(), ::toDetailsActivity)
        rvSearchResult.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(context, 2)
                } else {
                    LinearLayoutManager(context)
                }
            adapter = this@SearchMovieActivity.adapter
        }
    }

    private fun loadMovies(query: String?) {
        pagingJob?.cancel()
        query?.let {
            viewModel.query.value = it
        }
        pagingJob = lifecycleScope.launch(Dispatchers.Main) {
            adapter.refresh()
            viewModel.movieSearchResult.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun initSupportBar() {
        setSupportActionBar(binding.appBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService<SearchManager>()
        menu?.let {
            val searchView = it.findItem(R.id.search).actionView as SearchView
            with(searchView){
                setSearchableInfo(searchManager?.getSearchableInfo(componentName))
                queryHint = getString(R.string.hint_search_movie)
                setOnQueryTextListener(this@SearchMovieActivity)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { submittedText ->
            if (submittedText.isBlank() || submittedText.isEmpty()) {
                binding.root.showSnackbar(getString(R.string.message_query_null))
            } else {
                Timber.d(submittedText)
                loadMovies(submittedText)
            }
        }
        return true
    }
    override fun onQueryTextChange(p0: String?): Boolean = false
}