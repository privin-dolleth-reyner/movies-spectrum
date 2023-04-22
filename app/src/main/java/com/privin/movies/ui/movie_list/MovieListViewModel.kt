package com.privin.movies.ui.movie_list

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privin.movies.ApiError
import com.privin.movies.R
import com.privin.movies.isInternetConnected
import com.privin.movies.model.Movie
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class MovieListViewModel constructor(
    @ApplicationContext private val context: dagger.Lazy<Context>,
): ViewModel() {


    protected val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>> = _movies

    protected val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: SharedFlow<String> = _error

    var nextPage = 1L

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(this::class.simpleName, "Coroutine exception: $throwable")
        viewModelScope.launch {
            when(throwable){
                is ApiError.UnAuthorized -> _error.emit(context.get().getString(R.string.err_401))
                is ApiError.PageNotFound -> _error.emit(context.get().getString(R.string.err_404))
                else -> {
                    if (context.get().isInternetConnected().not())
                        _error.emit(context.get().getString(R.string.err_no_internet))
                    else
                        _error.emit(context.get().getString(R.string.err))
                }
            }
        }
    }

}