package com.privin.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privin.movies.domain.GetMoviesPlayingNow
import com.privin.movies.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun loadNowPlaying(){
        viewModelScope.launch {
            _nowPlayingMovies.postValue(getMoviesPlayingNow.get().execute())
        }
    }
}