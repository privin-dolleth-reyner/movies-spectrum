package com.privin.movies.ui.movie_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.privin.movies.R
import com.privin.movies.model.Movie
import com.privin.movies.ui.HomeActivity
import com.privin.movies.ui.HomeViewModel
import com.privin.movies.ui.movie_detail.MovieDetailFragment

class NowPlayingFragment : MovieListFragment() {

    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        viewModel.loadNowPlaying()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.nowPlayingMovies.observe(requireActivity()) {
            if (viewModel.nextPageNowPlaying > 1 && it.isEmpty()) {
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
        viewModel.loadNowPlaying(viewModel.nextPageNowPlaying)
    }

}