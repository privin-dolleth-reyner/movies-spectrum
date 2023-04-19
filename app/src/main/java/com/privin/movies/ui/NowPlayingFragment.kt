package com.privin.movies.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.privin.movies.R

class NowPlayingFragment : MovieListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNowPlaying()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.nowPlayingMovies.observe(requireActivity()) {
            if(it.isEmpty()){
                Toast.makeText(requireContext(), getString(R.string.end_of_list), Toast.LENGTH_SHORT).show()
            }
            movieAdapter.addMovieList(it)
        }
    }

    override fun loadMore() {
        viewModel.loadNowPlaying(viewModel.nextPageNowPlaying)
    }

}