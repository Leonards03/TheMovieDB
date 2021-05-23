package com.dicoding.bfaa.tmdb.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.bfaa.tmdb.databinding.ActivityDetailTvShowBinding

class DetailTvShowActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetailTvShowBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}