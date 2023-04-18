package com.privin.movies.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privin.movies.domain.GetMoviesPlayingNow
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

    fun loadData(){
        Log.d(TAG, "loadData: ")
        viewModelScope.launch {
            val size = getMoviesPlayingNow.get().execute().size
            Log.d(TAG, "loadData: $size")
        }
    }
}