package com.privin.movies.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privin.movies.domain.SearchMovies
import com.privin.movies.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovies: dagger.Lazy<SearchMovies>
) : ViewModel() {
    companion object {
        private const val TAG = "SearchViewModel"
    }

    private val _searchResultMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val searchResultMovies: LiveData<List<Movie>> = _searchResultMovies

    var page = 1L
    var searchQuery = ""

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception: ${throwable.message}")
    }

    fun searchMovies(searchQuery: String, page: Long = 1) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = searchMovies.get().execute(searchQuery, page)
            val movieList = result.first
            _searchResultMovies.postValue(movieList)
        }
    }
}