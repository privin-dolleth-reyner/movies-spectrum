package com.privin.movies.ui.favourite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.privin.movies.ui.movie_list.MovieListFragment
import kotlinx.coroutines.flow.collectLatest

class FavouriteFragment : MovieListFragment() {

    companion object {
        const val TAG = "FavouriteFragment"
    }

    lateinit var viewModel: FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FavouriteViewModel::class.java]
        viewModel.getAllFavMovies()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favouriteMovies.observe(requireActivity()) {
            binding.emptyView.isVisible = it.isEmpty()
            binding.loader.isVisible = false
            movieAdapter.updateMovieList(it)
        }
    }

    override fun loadMore() {
        // do nothing
    }

    override suspend fun onError() {
        viewModel.error.collectLatest {
            binding.loader.isVisible = false
            bindingError.errorText.text = it
            bindingError.errorGrp.isVisible = true
        }
    }

    override fun onRetry() {
        viewModel.getAllFavMovies()
    }
}