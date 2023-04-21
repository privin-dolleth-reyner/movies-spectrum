package com.privin.movies.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.privin.movies.ui.movie_list.MovieListFragment

class SearchFragment : MovieListFragment() {

    lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loader.isVisible = false
        binding.searchInput.apply {
            isVisible = true
            requestFocus()
            doOnTextChanged { text, start, before, count ->
                viewModel.searchQuery = text.toString()
                viewModel.page = 1L

                viewModel.searchMovies(viewModel.searchQuery, viewModel.page)
            }
        }
        binding.back.apply {
            isVisible = true
            setOnClickListener {
                activity?.finish()
            }
        }
        viewModel.searchResultMovies.observe(requireActivity()) {
            if (viewModel.page > 1) {
                movieAdapter.addMovieList(it)
            } else {
                movieAdapter.updateMovieList(it)
                scrollToTop()
            }
            binding.loader.isVisible = false
        }
    }

    override fun loadMore() {
        viewModel.page += 1
        viewModel.searchMovies(viewModel.searchQuery, viewModel.page)
    }
}