package com.privin.movies.ui.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.privin.movies.R
import com.privin.movies.model.Movie
import com.privin.movies.ui.HomeActivity
import com.privin.movies.ui.HomeViewModel
import com.privin.movies.ui.movie_detail.MovieDetailFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

    override suspend fun onError() {
        viewModel.error.collectLatest {
            binding.loader.isVisible = false
            bindingError.errorText.text = it
            bindingError.errorGrp.isVisible = true
        }
    }

    override fun onRetry() {
        viewModel.nextPageNowPlaying = 1L
        viewModel.loadNowPlaying()
    }


}