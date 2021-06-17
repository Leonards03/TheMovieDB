package com.dicoding.bfaa.tmdb.home.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bfaa.tmdb.core.data.states.ItemType
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.extension.invisible
import com.dicoding.bfaa.tmdb.core.extension.visible
import com.dicoding.bfaa.tmdb.core.presentation.adapter.FavoriteAdapter
import com.dicoding.bfaa.tmdb.core.utils.EspressoIdlingResource
import com.dicoding.bfaa.tmdb.databinding.FragmentFavoriteMovieBinding
import com.dicoding.bfaa.tmdb.detail.DetailActivity
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_TYPE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private var binding: FragmentFavoriteMovieBinding? = null
    private val viewModel by viewModels<MovieViewModel>()
    private val adapter = FavoriteAdapter<Movie> { intentToDetailsActivity(it.id) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadFavoriteMovies()
    }

    private fun setupRecyclerView() = binding?.apply {
        rvFavoriteMovie.setHasFixedSize(true)
        rvFavoriteMovie.layoutManager = LinearLayoutManager(context)
        rvFavoriteMovie.adapter = adapter
    }

    private fun loadFavoriteMovies() {
        EspressoIdlingResource.increment()
        viewModel.favoriteMovies.observe(viewLifecycleOwner, ::renderMovies)
        if(!EspressoIdlingResource.espressoTestIdlingResource.isIdleNow)
            EspressoIdlingResource.decrement()
    }

    private fun renderMovies(list: List<Movie>) {
        adapter.submitData(list)
        binding?.apply {
            if (list.isEmpty()){
                stateEmpty.root.visible()
            } else {
                stateEmpty.root.invisible()
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