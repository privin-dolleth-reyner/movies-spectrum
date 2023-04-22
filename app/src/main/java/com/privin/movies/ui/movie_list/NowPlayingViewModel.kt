package com.privin.movies.ui.movie_list

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.privin.movies.domain.GetMoviesPlayingNow
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    @ApplicationContext private val context: dagger.Lazy<Context>,
    private val getMoviesPlayingNow: dagger.Lazy<GetMoviesPlayingNow>,
) : MovieListViewModel(context) {

    fun loadNowPlaying(page: Long = 1){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = getMoviesPlayingNow.get().execute(page)
            val movieList = result.first
            _movies.postValue(movieList)
            if (movieList.isNotEmpty()) {
                nextPage = result.second + 1
            }
        }
    }
}