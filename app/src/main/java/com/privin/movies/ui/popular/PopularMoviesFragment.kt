package com.privin.movies.ui.popular

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.privin.movies.R
import com.privin.movies.ui.movie_list.MovieListFragment
import kotlinx.coroutines.flow.collectLatest

class PopularMoviesFragment: MovieListFragment() {

    private lateinit var viewModel: PopularMoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[PopularMoviesViewModel::class.java]
        viewModel.loadPopularMovies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movies.observe(requireActivity()) {
            if (viewModel.nextPage > 1 && it.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.end_of_list),
                    Toast.LENGTH_SHORT
                ).show()
            }
            bindingLoader.loaderGrp.isVisible = false
            movieAdapter.addMovieList(it)
        }
    }

    override fun loadMore() {
        viewModel.loadPopularMovies(viewModel.nextPage)
    }

    override suspend fun onError() {
        viewModel.error.collectLatest {
            bindingLoader.loaderGrp.isVisible = false
            bindingError.errorText.text = it
            bindingError.errorGrp.isVisible = true
        }
    }

    override fun onRetry() {
        viewModel.nextPage = 1L
        viewModel.loadPopularMovies()
    }

}