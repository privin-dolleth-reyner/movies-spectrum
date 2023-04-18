package com.privin.movies.data.remote

import com.privin.movies.data.model.MovieResponse

interface Server {
    suspend fun getNowPlayingMovies(): MovieResponse
}