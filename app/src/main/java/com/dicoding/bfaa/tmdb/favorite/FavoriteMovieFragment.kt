package com.dicoding.bfaa.tmdb.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.presentation.adapter.FavoriteAdapter
import com.dicoding.bfaa.tmdb.databinding.FragmentFavoriteMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavoriteMovieFragment : Fragment() {
    private var binding: FragmentFavoriteMovieBinding? = null

    private val viewModel by viewModels<FavoriteViewModel>()

    private val favoriteMoviesAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter()
    }

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
        setupObservers()
    }

    private fun setupRecyclerView() = binding?.apply {
        rvFavoriteMovie.setHasFixedSize(true)
        rvFavoriteMovie.layoutManager = GridLayoutManager(context,2)
        rvFavoriteMovie.adapter = favoriteMoviesAdapter
    }

    private fun setupObservers() {
//        lifecycleScope.launch {
//            viewModel.favoriteMovies.collectLatest {
//                favoritePagingDataAdapter.submitData(it)
//            }
//        }
        viewModel.favoriteMovies.observe(viewLifecycleOwner, ::observeFavoriteMovies)

    }

    private fun observeFavoriteMovies(resource: List<Movie>) {
        Timber.d(resource.toString())
        favoriteMoviesAdapter.submitMovieList(resource)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}