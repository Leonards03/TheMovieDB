package com.dicoding.tmdb.favorite.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tmdb.app.extension.intentToDetailsActivity
import com.dicoding.tmdb.app.utils.AppPreferences
import com.dicoding.tmdb.core.domain.model.DomainModel
import com.dicoding.tmdb.core.extension.invisible
import com.dicoding.tmdb.core.extension.visible
import com.dicoding.tmdb.core.presentation.adapter.FavoriteAdapter
import com.dicoding.tmdb.favorite.databinding.FragmentFavoriteBinding
import com.dicoding.tmdb.favorite.presentation.factory.ViewModelFactory
import com.dicoding.tmdb.favorite.ui.FavoriteViewModel
import javax.inject.Inject

abstract class BaseFavoriteFragment<T : DomainModel> : Fragment() {
    private var binding: FragmentFavoriteBinding? = null
    private lateinit var adapter: FavoriteAdapter<T>

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var appPreferences: AppPreferences
    protected val viewModel: FavoriteViewModel by activityViewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadData()
    }

    private fun setupRecyclerView() = binding?.apply {
        adapter = FavoriteAdapter(appPreferences.getImageSize(), ::intentToDetailsActivity)
        rvFavorite.setHasFixedSize(true)
        rvFavorite.layoutManager = LinearLayoutManager(context)
        rvFavorite.adapter = adapter
    }

    protected abstract fun loadData()

    protected fun render(list: List<T>) {
        adapter.submitData(list)
        binding?.apply {
            if (list.isEmpty()) {
                stateEmpty.root.visible()
            } else {
                stateEmpty.root.invisible()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}