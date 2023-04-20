package com.privin.movies.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privin.movies.domain.GetMoviesPlayingNow
import com.privin.movies.domain.GetPopularMovies
import com.privin.movies.domain.GetTopRatedMovies
import com.privin.movies.domain.GetUpComingMovies
import com.privin.movies.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesPlayingNow: dagger.Lazy<GetMoviesPlayingNow>,
    private val getPopularMovies: dagger.Lazy<GetPopularMovies>,
    private val getUpComingMovies: dagger.Lazy<GetUpComingMovies>,
    private val getTopRatedMovies: dagger.Lazy<GetTopRatedMovies>,
): ViewModel() {
    companion object{
        const val TAG = "HomeViewModel"
    }

    private val _nowPlayingMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val nowPlayingMovies: LiveData<List<Movie>> = _nowPlayingMovies

    private val _popularMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    private val _upcomingMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val upcomingMovies: LiveData<List<Movie>> = _upcomingMovies

    private val _topRatedMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val topRatedMovies: LiveData<List<Movie>> = _topRatedMovies

    var nextPageNowPlaying = 1L
    var nextPagePopularMovies = 1L
    var nextPageUpcomingMovies = 1L
    var nextPageTopRatedMovies = 1L

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

    fun loadPopularMovies(page: Long = 1){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = getPopularMovies.get().execute(page)
            val movieList = result.first
            _popularMovies.postValue(movieList)
            if(movieList.isNotEmpty()){
                nextPagePopularMovies = result.second + 1
            }
        }
    }

    fun loadUpcomingMovies(page: Long = 1){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = getUpComingMovies.get().execute(page)
            val movieList = result.first
            _upcomingMovies.postValue(movieList)
            if(movieList.isNotEmpty()){
                nextPageUpcomingMovies = result.second + 1
            }
        }
    }
    fun loadTopRatedMovies(page: Long = 1){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = getTopRatedMovies.get().execute(page)
            val movieList = result.first
            _topRatedMovies.postValue(movieList)
            if(movieList.isNotEmpty()){
                nextPageTopRatedMovies = result.second + 1
            }
        }
    }
}