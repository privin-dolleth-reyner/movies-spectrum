package com.privin.movies.ui.movie_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.privin.movies.R
import com.privin.movies.model.Movie
import com.privin.movies.ui.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

class UpComingMoviesFragment: MovieListFragment() {

    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        viewModel.loadUpcomingMovies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.upcomingMovies.observe(requireActivity()) {
            if (viewModel.nextPagePopularMovies > 1 && it.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.end_of_list),
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.loader.isVisible = false
            movieAdapter.addMovieList(it)
        }
    }

    override fun loadMore() {
        viewModel.loadUpcomingMovies(viewModel.nextPageUpcomingMovies)
    }

    override suspend fun onError() {
        viewModel.error.collectLatest {
            binding.loader.isVisible = false
            binding.errorText.text = it
            binding.errorGrp.isVisible = true
        }
    }

    override fun onRetry() {
        viewModel.nextPageUpcomingMovies = 1L
        viewModel.loadUpcomingMovies()
    }

}