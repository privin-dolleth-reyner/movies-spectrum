package com.privin.movies.ui.movie_list

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.privin.movies.domain.GetPopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    @ApplicationContext private val context: dagger.Lazy<Context>,
    private val getPopularMovies: dagger.Lazy<GetPopularMovies>,
) : MovieListViewModel(context) {

    fun loadPopularMovies(page: Long = 1) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = getPopularMovies.get().execute(page)
            val movieList = result.first
            _movies.postValue(movieList)
            if (movieList.isNotEmpty()) {
                nextPage = result.second + 1
            }
        }
    }
}