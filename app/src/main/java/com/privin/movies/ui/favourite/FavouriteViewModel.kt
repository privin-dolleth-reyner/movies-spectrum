package com.privin.movies.ui.favourite

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privin.movies.ApiError
import com.privin.movies.R
import com.privin.movies.domain.GetFavouriteMovies
import com.privin.movies.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getFavouriteMovies: dagger.Lazy<GetFavouriteMovies>,
    @ApplicationContext private val context: dagger.Lazy<Context>,
) : ViewModel() {
    companion object {
        private const val TAG = "FavouriteViewModel"
    }

    private val _favouriteMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val favouriteMovies: LiveData<List<Movie>> = _favouriteMovies

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: SharedFlow<String> = _error

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception: $throwable")
        viewModelScope.launch {
            when (throwable) {
                is ApiError.UnAuthorized -> _error.emit(context.get().getString(R.string.err_401))
                is ApiError.PageNotFound -> _error.emit(context.get().getString(R.string.err_404))
                else -> _error.emit(context.get().getString(R.string.err))
            }
        }
    }

    fun getAllFavMovies() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            getFavouriteMovies.get().execute().collect {
                _favouriteMovies.postValue(it)
            }
        }
    }
}