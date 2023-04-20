package com.privin.movies.ui.movie_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible

class SearchFragment: MovieListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loader.isVisible = false
        binding.searchInput.apply {
            isVisible = true
            requestFocus()
        }
        binding.back.apply {
            isVisible = true
            setOnClickListener {
                activity?.finish()
            }
        }
    }
    override fun loadMore() {

    }
}