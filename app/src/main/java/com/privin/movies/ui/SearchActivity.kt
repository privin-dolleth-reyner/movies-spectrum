package com.privin.movies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.privin.movies.R
import com.privin.movies.databinding.ActivitySearchBinding
import com.privin.movies.ui.movie_list.SearchFragment

class SearchActivity: AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(binding.container.id, SearchFragment(), "search")
        ft.commit()
    }
}