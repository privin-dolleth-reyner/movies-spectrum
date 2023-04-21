package com.privin.movies.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.privin.movies.ui.movie_list.MovieListFragment
import kotlinx.coroutines.flow.collectLatest

class SearchFragment : MovieListFragment() {

    companion object{
        const val TAG = "SearchFragment"
    }

    lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loader.isVisible = false
        observeData()
        initSearchBar()
    }

    private fun initSearchBar(){
        binding.searchBar.visibility = View.VISIBLE
        binding.searchInput.apply {
            requestFocus()
            doOnTextChanged { text, _, _, _ ->
                viewModel.searchQuery = text.toString()
                viewModel.page = 1L

                viewModel.searchMovies(viewModel.searchQuery, viewModel.page)
            }
        }

        binding.back.apply {
            setOnClickListener {
                activity?.finish()
            }
        }
    }
    private fun observeData(){
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

    override suspend fun onError() {
        viewModel.error.collectLatest {
            binding.loader.isVisible = false
            binding.errorText.text = it
            binding.errorGrp.isVisible = true
        }
    }

    override fun onRetry() {
        viewModel.page = 1L
        viewModel.searchMovies(viewModel.searchQuery, viewModel.page)
    }
}