package com.privin.movies.ui.movie_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.privin.movies.R
import com.privin.movies.model.Movie

class UpComingMoviesFragment: MovieListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

}