package com.privin.movies.ui.movie_list

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.privin.movies.domain.GetMoviesPlayingNow
import com.privin.movies.domain.GetPopularMovies
import com.privin.movies.domain.GetTopRatedMovies
import com.privin.movies.domain.GetUpComingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    @ApplicationContext private val context: dagger.Lazy<Context>,
    private val getTopRatedMovies: dagger.Lazy<GetTopRatedMovies>,
) : MovieListViewModel(context) {

    fun loadTopRatedMovies(page: Long = 1){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = getTopRatedMovies.get().execute(page)
            val movieList = result.first
            _movies.postValue(movieList)
            if (movieList.isNotEmpty()) {
                nextPage = result.second + 1
            }
        }
    }
}