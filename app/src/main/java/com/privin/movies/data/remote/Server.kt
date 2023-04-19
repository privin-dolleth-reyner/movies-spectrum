package com.privin.movies.data.remote

import com.privin.movies.data.model.MovieResponse

interface Server {
    suspend fun getNowPlayingMovies(page: Long): MovieResponse
    suspend fun getPopularMovies(page: Long): MovieResponse
}