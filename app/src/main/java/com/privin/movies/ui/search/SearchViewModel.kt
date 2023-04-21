package com.privin.movies.ui.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privin.movies.ApiError
import com.privin.movies.R
import com.privin.movies.domain.SearchMovies
import com.privin.movies.model.Movie
import com.privin.movies.ui.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovies: dagger.Lazy<SearchMovies>,
    @ApplicationContext private val context: dagger.Lazy<Context>,
) : ViewModel() {
    companion object {
        private const val TAG = "SearchViewModel"
    }

    private val _searchResultMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val searchResultMovies: LiveData<List<Movie>> = _searchResultMovies

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: SharedFlow<String> = _error

    var page = 1L
    var searchQuery = ""

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(HomeViewModel.TAG, "Coroutine exception: $throwable")
        viewModelScope.launch {
            when(throwable){
                is ApiError.UnAuthorized -> _error.emit(context.get().getString(R.string.err_401))
                is ApiError.PageNotFound -> _error.emit(context.get().getString(R.string.err_404))
                else -> _error.emit(context.get().getString(R.string.err))
            }
        }
    }

    fun searchMovies(searchQuery: String, page: Long = 1) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = searchMovies.get().execute(searchQuery, page)
            val movieList = result.first
            _searchResultMovies.postValue(movieList)
        }
    }
}