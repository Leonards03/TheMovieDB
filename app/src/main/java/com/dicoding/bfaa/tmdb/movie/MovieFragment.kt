package com.dicoding.bfaa.tmdb.movie

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.bfaa.tmdb.R
import com.dicoding.bfaa.tmdb.core.data.states.Resource
import com.dicoding.bfaa.tmdb.core.data.states.Resource.*
import com.dicoding.bfaa.tmdb.core.domain.model.Movie
import com.dicoding.bfaa.tmdb.core.extension.checkConnectivity
import com.dicoding.bfaa.tmdb.core.extension.invisible
import com.dicoding.bfaa.tmdb.core.presentation.adapter.ItemAdapter
import com.dicoding.bfaa.tmdb.databinding.FragmentMovieBinding
import com.dicoding.bfaa.tmdb.databinding.HeaderLayoutBinding
import com.dicoding.bfaa.tmdb.detail.DetailActivity
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.EXTRA_TYPE
import com.dicoding.bfaa.tmdb.detail.DetailViewModel.Companion.MOVIE_TYPE
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private val viewModel: MovieViewModel by viewModels()

    private val discoverAdapter: ItemAdapter by lazy { ItemAdapter() }
    private val nowPlayingAdapter: ItemAdapter by lazy { ItemAdapter() }
    private val upcomingAdapter: ItemAdapter by lazy { ItemAdapter() }
    private val topRatedAdapter: ItemAdapter by lazy { ItemAdapter() }
    private val popularAdapter: ItemAdapter by lazy { ItemAdapter() }

    private var binding: FragmentMovieBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            if (requireActivity().checkConnectivity()) {
                connectionState(true)
                setupRecyclerView()
                setupObserver()
            } else {
                connectionState(false)
            }
        }
    }

    private fun connectionState(available: Boolean) = binding?.apply {
        if (available) {
            noConnectionState.root.invisible()
            setLoadingState(true)
        } else {
            noConnectionState.btnRetry.setOnClickListener {
//                setupRecyclerView()
//                setupObserver()
            }
        }
    }


    private fun initHorizontalRecyclerView(
        recyclerView: RecyclerView,
        itemAdapter: ItemAdapter,
        headerLayoutBinding: HeaderLayoutBinding,
        title: String,
    ) {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = itemAdapter
            itemAdapter.layoutOrientation = ItemAdapter.HORIZONTAL
            GravitySnapHelper(Gravity.CENTER)
                .attachToRecyclerView(recyclerView)
        }

        with(headerLayoutBinding) {
            tvHeader.text = title
        }

    }


    private fun setupRecyclerView() = binding?.apply {
        initHorizontalRecyclerView(rvNowPlaying,
            nowPlayingAdapter,
            headerNowPlaying,
            getString(R.string.title_now_playing))
        nowPlayingAdapter.setOnMovieClickCallback(object : ItemAdapter.OnMovieClickCallback {
            override fun onItemClicked(data: Movie) = intentToDetailsActivity(data.id)
        })

        initHorizontalRecyclerView(rvDiscoverMovie,
            discoverAdapter,
            headerDiscover,
            getString(R.string.title_discover_movie))
        discoverAdapter.setOnMovieClickCallback(object : ItemAdapter.OnMovieClickCallback {
            override fun onItemClicked(data: Movie) = intentToDetailsActivity(data.id)
        })

        initHorizontalRecyclerView(rvUpcoming,
            upcomingAdapter,
            headerUpcoming,
            getString(R.string.title_upcoming_movie))
        upcomingAdapter.setOnMovieClickCallback(object : ItemAdapter.OnMovieClickCallback {
            override fun onItemClicked(data: Movie) = intentToDetailsActivity(data.id)
        })

        initHorizontalRecyclerView(rvToprated,
            topRatedAdapter,
            headerToprated,
            getString(R.string.title_top_rated_movie))
        topRatedAdapter.setOnMovieClickCallback(object : ItemAdapter.OnMovieClickCallback {
            override fun onItemClicked(data: Movie) = intentToDetailsActivity(data.id)
        })

        initHorizontalRecyclerView(rvPopular,
            popularAdapter,
            headerPopular,
            getString(R.string.title_popular_movie))
        popularAdapter.setOnMovieClickCallback(object : ItemAdapter.OnMovieClickCallback {
            override fun onItemClicked(data: Movie) = intentToDetailsActivity(data.id)
        })
    }


    /**
     * Setup the required observer on this Fragment
     */
    private fun setupObserver() = with(viewModel) {
        discoverMovie.observe(viewLifecycleOwner, ::observeDiscoverMovie)
        nowPlaying.observe(viewLifecycleOwner, ::observeNowPlaying)
        upcomingMovies.observe(viewLifecycleOwner, ::observeUpcomingMovies)
        topRatedMovies.observe(viewLifecycleOwner, ::observeTopRatedMovies)
        popularMovies.observe(viewLifecycleOwner, ::observePopularMovies)
    }

    /**
     * functions act as the observer to the data that are requested by this fragment
     */
    private fun observeNowPlaying(resource: Resource<List<Movie>>) = when (resource) {
        is Error -> errorHandling(resource.message)
        is Loading -> setLoadingState(true)
        is Success -> bindRecyclerView(nowPlayingAdapter, resource.data)
    }

    private fun observeDiscoverMovie(resource: Resource<List<Movie>>) = when (resource) {
        is Error -> errorHandling(resource.message)
        is Loading -> setLoadingState(true)
        is Success -> bindRecyclerView(discoverAdapter, resource.data)
    }

    private fun observeUpcomingMovies(resource: Resource<List<Movie>>) = when (resource) {
        is Error -> errorHandling(resource.message)
        is Loading -> setLoadingState(true)
        is Success -> bindRecyclerView(upcomingAdapter, resource.data)
    }

    private fun observeTopRatedMovies(resource: Resource<List<Movie>>) = when (resource) {
        is Error -> errorHandling(resource.message)
        is Loading -> setLoadingState(true)
        is Success -> bindRecyclerView(topRatedAdapter, resource.data)
    }

    private fun observePopularMovies(resource: Resource<List<Movie>>) = when (resource) {
        is Error -> errorHandling(resource.message)
        is Loading -> setLoadingState(true)
        is Success -> bindRecyclerView(popularAdapter, resource.data)
    }

//    private fun <T> observeList(resource: T, action: (adapter: ItemAdapter, data: T)-> Unit) =
//        when(resource){}
//


    /**
     * Function to bind data to the target RecyclerView
     */
    private fun bindRecyclerView(adapter: ItemAdapter, data: List<Movie>) = with(adapter) {
        Timber.d(data.toString())
        setLoadingState(false)
        submitItemList(data)
        notifyDataSetChanged()
    }


    private fun setLoadingState(isLoading: Boolean) {
        binding?.apply {
//            if (isLoading) {
//                progressBar.visible()
//            } else {
//                progressBar.invisible()
//            }
        }
    }

    private fun intentToDetailsActivity(id: Int) =
        Intent(requireActivity(), DetailActivity::class.java).run {
            putExtra(EXTRA_ID, id)
            putExtra(EXTRA_TYPE, MOVIE_TYPE)
            startActivity(this)
        }

    private fun errorHandling(message: String?) =
        Timber.e(message)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}