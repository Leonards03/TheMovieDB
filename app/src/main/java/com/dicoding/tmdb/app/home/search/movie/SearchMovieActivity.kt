package com.dicoding.tmdb.app.home.search.movie

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tmdb.app.databinding.ActivitySearchMovieBinding
import com.dicoding.tmdb.app.detail.DetailActivity
import com.dicoding.tmdb.app.detail.DetailViewModel
import com.dicoding.tmdb.app.home.search.SearchViewModel
import com.dicoding.tmdb.app.utils.AppPreferences
import com.dicoding.tmdb.core.data.states.ItemType
import com.dicoding.tmdb.core.domain.model.Movie
import com.dicoding.tmdb.core.extension.invisible
import com.dicoding.tmdb.core.presentation.adapter.MoviePagedAdapter
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
        ActivitySearchMovieBinding.inflate(layoutInflater)
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
        adapter = MoviePagedAdapter(appPreferences.getImageSize(), ::intentToDetailsActivity)
        rvSearchMovie.apply {
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
        binding.stateEmpty.invisible()
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

    private fun intentToDetailsActivity(movie: Movie) =
        Intent(this, DetailActivity::class.java).run {
            putExtra(DetailViewModel.EXTRA_ID, movie.id)
            putExtra(DetailViewModel.EXTRA_TYPE, ItemType.Movie)
            startActivity(this)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}