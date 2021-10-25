package com.leonards.tmdb.app.extension

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.leonards.tmdb.app.detail.DetailActivity
import com.leonards.tmdb.app.detail.DetailViewModel.Companion.EXTRA_ID
import com.leonards.tmdb.app.detail.DetailViewModel.Companion.EXTRA_TYPE
import com.leonards.tmdb.core.data.states.ItemType
import com.leonards.tmdb.core.domain.model.DomainModel
import com.leonards.tmdb.core.domain.model.Movie
import com.leonards.tmdb.core.domain.model.TvShow

fun Fragment.intentToDetailsActivity(item: DomainModel) =
    Intent(requireActivity(), DetailActivity::class.java).run {
        putExtra(EXTRA_ID, item.id)
        val type = when(item){
            is Movie -> ItemType.Movie
            is TvShow -> ItemType.TvShow
        }
        putExtra(EXTRA_TYPE, type)
        startActivity(this)
    }

fun AppCompatActivity.toDetailsActivity(item: DomainModel) =
    Intent(this, DetailActivity::class.java).run {
        putExtra(EXTRA_ID, item.id)
        val type = when(item){
            is Movie -> ItemType.Movie
            is TvShow -> ItemType.TvShow
        }
        putExtra(EXTRA_TYPE, type)
        startActivity(this)
    }
