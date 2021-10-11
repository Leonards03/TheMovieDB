package com.leonards.tmdb.app.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.leonards.tmdb.app.R
import com.leonards.tmdb.app.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private lateinit var bottomNavConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarLayout.appBar)

        initBottomNav()
    }

    private fun initBottomNav() {
        val navController = findNavController(R.id.nav_host)
        bottomNavConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_movie,
            R.id.navigation_tv_show,
            R.id.navigation_favorite,
            R.id.navigation_settings
        ).build()

        setupActionBarWithNavController(navController, bottomNavConfiguration)
        binding.bottomNavView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.nav_host)
            .navigateUp(bottomNavConfiguration) || super.onSupportNavigateUp()
}
