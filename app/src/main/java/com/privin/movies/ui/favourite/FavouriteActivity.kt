package com.privin.movies.ui.favourite

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.privin.movies.R
import com.privin.movies.databinding.ActivityFavouriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityFavouriteBinding

    lateinit var viewModel: FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.favourites)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        initFavFragment()
    }

    private fun initFavFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(binding.container.id, FavouriteFragment(), FavouriteFragment.TAG)
        ft.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}