package com.privin.movies.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.privin.movies.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding

    lateinit var viewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSearchFragment()
    }

    private fun initSearchFragment(){
        supportActionBar?.hide()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(binding.container.id, SearchFragment(), SearchFragment.TAG)
        ft.commit()
    }
}