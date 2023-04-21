package com.privin.movies.ui.favourite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.privin.movies.R
import com.privin.movies.databinding.ActivityFavouriteBinding

class FavouriteActivity: AppCompatActivity() {

    lateinit var binding: ActivityFavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.favourites)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
}