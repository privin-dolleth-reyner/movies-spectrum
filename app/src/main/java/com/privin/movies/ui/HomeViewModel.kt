package com.privin.movies.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privin.movies.domain.GetMoviesPlayingNow
import com.privin.movies.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesPlayingNow: dagger.Lazy<GetMoviesPlayingNow>
): ViewModel() {
    companion object{
        const val TAG = "HomeViewModel"
    }

    private val _nowPlayingMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val nowPlayingMovies: LiveData<List<Movie>> = _nowPlayingMovies

    var nextPageNowPlaying = 1L

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e(TAG, "Coroutine exception: ${throwable.message}")
    }
    fun loadNowPlaying(page: Long = 1){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = getMoviesPlayingNow.get().execute(page)
            val movieList = result.first
            _nowPlayingMovies.postValue(movieList)
            if(movieList.isNotEmpty()){
                nextPageNowPlaying = result.second + 1
            }
        }
    }
}