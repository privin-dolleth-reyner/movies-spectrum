package com.privin.movies.ui.movie_list

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.privin.movies.domain.GetUpComingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    @ApplicationContext private val context: dagger.Lazy<Context>,
    private val getUpComingMovies: dagger.Lazy<GetUpComingMovies>,
) : MovieListViewModel(context) {

    fun loadUpcomingMovies(page: Long = 1) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = getUpComingMovies.get().execute(page)
            val movieList = result.first
            _movies.postValue(movieList)
            if (movieList.isNotEmpty()) {
                nextPage = result.second + 1
            }
        }
    }
}