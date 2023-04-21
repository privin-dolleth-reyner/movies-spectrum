package com.privin.movies.ui.movie_detail

import android.util.Log
import androidx.lifecycle.*
import com.privin.movies.domain.GetMovieDetail
import com.privin.movies.model.Movie
import com.privin.movies.model.MovieDetail
import com.privin.movies.ui.HomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetail: dagger.Lazy<GetMovieDetail>,
) : ViewModel() {

    companion object {
        private const val TAG = "MovieDetailViewModel"
    }

    private var id: Long = savedStateHandle.get<Long>("id") ?: 0L


    private val _movieDetail: MutableLiveData<MovieDetail> = MutableLiveData()
    val movieDetail: LiveData<MovieDetail> = _movieDetail


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception: ${throwable.message}")
    }

    fun loadMovieDetail() {
        Log.d(TAG, "loadData: $id")
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val detail = getMovieDetail.get().execute(id)
            _movieDetail.postValue(detail)
        }
    }
}