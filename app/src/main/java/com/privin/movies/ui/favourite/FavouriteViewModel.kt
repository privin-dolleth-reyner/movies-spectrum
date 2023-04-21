package com.privin.movies.ui.favourite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privin.movies.domain.GetFavouriteMovies
import com.privin.movies.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getFavouriteMovies: dagger.Lazy<GetFavouriteMovies>
): ViewModel() {
    companion object {
        private const val TAG = "FavouriteViewModel"
    }

    private val _favouriteMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val favouriteMovies: LiveData<List<Movie>> = _favouriteMovies


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception: ${throwable.message}")
    }

    fun getAllFavMovies(){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            getFavouriteMovies.get().execute().collect{
                _favouriteMovies.postValue(it)
            }
        }
    }
}