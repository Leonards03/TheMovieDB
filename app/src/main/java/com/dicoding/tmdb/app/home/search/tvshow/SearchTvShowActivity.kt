package com.dicoding.tmdb.app.home.search.tvshow

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tmdb.app.databinding.ActivitySearchResultBinding
import com.dicoding.tmdb.app.extension.toDetailsActivity
import com.dicoding.tmdb.app.home.search.SearchViewModel
import com.dicoding.tmdb.app.utils.AppPreferences
import com.dicoding.tmdb.core.presentation.adapter.TvShowPagedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SearchTvShowActivity : AppCompatActivity() {
    private val viewModel: SearchViewModel by viewModels()
    private val binding by lazy {
        ActivitySearchResultBinding.inflate(layoutInflater)
    }
    private var pagingJob: Job? = null
    private lateinit var adapter: TvShowPagedAdapter

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initSupportBar()
        setupRecyclerView()
        loadTvShows()
    }

    private fun setupRecyclerView() = with(binding) {
        adapter = TvShowPagedAdapter(appPreferences.getImageSize(), ::toDetailsActivity)
        rvSearchResult.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    GridLayoutManager(context, 2)
                } else {
                    LinearLayoutManager(context)
                }
            adapter = this@SearchTvShowActivity.adapter
        }
    }

    private fun loadTvShows() {
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            viewModel.searchTvShow.collectLatest { pagingData ->
                Timber.d(pagingData.toString())
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