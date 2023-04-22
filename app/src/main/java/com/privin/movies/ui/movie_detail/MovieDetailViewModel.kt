package com.privin.movies.ui.movie_detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.privin.movies.ApiError
import com.privin.movies.R
import com.privin.movies.domain.AddToFavMovie
import com.privin.movies.domain.GetMovieDetail
import com.privin.movies.domain.IsFavMovie
import com.privin.movies.domain.RemoveFromFavMovie
import com.privin.movies.isInternetConnected
import com.privin.movies.model.MovieDetail
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
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetail: dagger.Lazy<GetMovieDetail>,
    private val addToFavMovie: dagger.Lazy<AddToFavMovie>,
    private val removeFromFavMovie: dagger.Lazy<RemoveFromFavMovie>,
    private val isFavMovie: dagger.Lazy<IsFavMovie>,
    @ApplicationContext private val context: dagger.Lazy<Context>,
) : ViewModel() {

    companion object {
        private const val TAG = "MovieDetailViewModel"
    }

    private var id: Long = savedStateHandle.get<Long>("id") ?: 0L


    private val _movieDetail: MutableLiveData<MovieDetail> = MutableLiveData()
    val movieDetail: LiveData<MovieDetail> = _movieDetail

    private val _favMovie: MutableLiveData<Boolean> = MutableLiveData()
    val favMovie: LiveData<Boolean> = _favMovie

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: SharedFlow<String> = _error

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(HomeViewModel.TAG, "Coroutine exception: $throwable")
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

    fun loadMovieDetail() {
        Log.d(TAG, "loadData: $id")
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val detail = getMovieDetail.get().execute(id)
            _movieDetail.postValue(detail)
        }
    }

    fun addToFavMovie(movieDetail: MovieDetail) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            addToFavMovie.get().execute(movieDetail)
        }
    }


    fun removeFromFavMovie(id: Long) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            removeFromFavMovie.get().execute(id)
        }
    }

    fun isFavMovie(id: Long) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val isFavMovie = isFavMovie.get().execute(id)
            _favMovie.postValue(isFavMovie)
        }
    }

}