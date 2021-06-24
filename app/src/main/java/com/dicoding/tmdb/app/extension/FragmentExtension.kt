package com.dicoding.tmdb.app.extension

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.tmdb.app.detail.DetailActivity
import com.dicoding.tmdb.app.detail.DetailViewModel.Companion.EXTRA_ID
import com.dicoding.tmdb.app.detail.DetailViewModel.Companion.EXTRA_TYPE
import com.dicoding.tmdb.core.domain.model.DomainModel

fun Fragment.intentToDetailsActivity(item: DomainModel) =
    Intent(requireActivity(), DetailActivity::class.java).run {
        putExtra(EXTRA_ID, item.id)
        putExtra(EXTRA_TYPE, item.type)
        startActivity(this)
    }

fun AppCompatActivity.toDetailsActivity(item: DomainModel) =
    Intent(this, DetailActivity::class.java).run {
        putExtra(EXTRA_ID, item.id)
        putExtra(EXTRA_TYPE, item.type)
        startActivity(this)
    }
