package com.dicoding.tmdb.favorite.presentation.favorite.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tmdb.app.detail.DetailActivity
import com.dicoding.tmdb.app.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.tmdb.app.detail.DetailViewModel.Companion.EXTRA_TYPE
import com.dicoding.tmdb.app.di.FavoriteModuleDependencies
import com.dicoding.tmdb.app.utils.AppPreferences
import com.dicoding.tmdb.core.data.states.ItemType
import com.dicoding.tmdb.core.domain.model.Movie
import com.dicoding.tmdb.core.extension.invisible
import com.dicoding.tmdb.core.extension.visible
import com.dicoding.tmdb.core.presentation.adapter.FavoriteAdapter
import com.dicoding.tmdb.favorite.databinding.FragmentFavoriteMovieBinding
import com.dicoding.tmdb.favorite.di.DaggerFavoriteComponent
import com.dicoding.tmdb.favorite.presentation.factory.ViewModelFactory
import com.dicoding.tmdb.favorite.presentation.favorite.FavoriteViewModel
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class MovieFragment : Fragment() {
    private var binding: FragmentFavoriteMovieBinding? = null
    private lateinit var adapter: FavoriteAdapter<Movie>

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var appPreferences: AppPreferences

    private val viewModel: FavoriteViewModel by activityViewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(EntryPointAccessors.fromApplication(
                context.applicationContext,
                FavoriteModuleDependencies::class.java
            ))
            .build()
            .inject(this)
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
        loadFavoriteMovies()
    }

    private fun setupRecyclerView() = binding?.apply {
        adapter = FavoriteAdapter(appPreferences.getImageSize(), ::intentToDetailsActivity)
        rvFavoriteMovie.setHasFixedSize(true)
        rvFavoriteMovie.layoutManager = LinearLayoutManager(context)
        rvFavoriteMovie.adapter = adapter
    }

    private fun loadFavoriteMovies() {
        viewModel.favoriteMovies.observe(viewLifecycleOwner, ::renderMovies)
    }

    private fun renderMovies(list: List<Movie>) {
        adapter.submitData(list)
        binding?.apply {
            if (list.isEmpty()) {
                stateEmpty.root.visible()
            } else {
                stateEmpty.root.invisible()
            }
        }
    }

    private fun intentToDetailsActivity(movie: Movie) =
        Intent(requireActivity(), DetailActivity::class.java).run {
            putExtra(EXTRA_ID, movie.id)
            putExtra(EXTRA_TYPE, ItemType.Movie)
            startActivity(this)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}