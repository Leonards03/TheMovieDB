package com.leonards.tmdb.app.home.search.movie

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonards.tmdb.app.databinding.ActivitySearchResultBinding
import com.leonards.tmdb.app.extension.toDetailsActivity
import com.leonards.tmdb.app.home.search.SearchViewModel
import com.leonards.tmdb.app.utils.AppPreferences
import com.leonards.tmdb.core.presentation.adapter.MoviePagedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchMovieActivity : AppCompatActivity() {
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
        loadMovies()
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

    private fun loadMovies() {
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch(Dispatchers.Main) {
            viewModel.searchMovie.collectLatest { pagingData ->
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
}