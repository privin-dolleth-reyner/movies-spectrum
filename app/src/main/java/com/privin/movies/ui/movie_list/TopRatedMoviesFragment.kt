package com.privin.movies.ui.movie_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.privin.movies.R
import com.privin.movies.model.Movie

class TopRatedMoviesFragment: MovieListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadTopRatedMovies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.topRatedMovies.observe(requireActivity()) {
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
        viewModel.loadTopRatedMovies(viewModel.nextPageUpcomingMovies)
    }

}