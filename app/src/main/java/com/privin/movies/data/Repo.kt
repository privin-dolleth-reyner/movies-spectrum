package com.privin.movies.data

import com.privin.movies.data.model.MovieResponse

interface Repo {

    suspend fun getMoviesNowPlaying(page: Long): MovieResponse
    suspend fun getPopularMovies(page: Long): MovieResponse
    suspend fun getUpcomingMovies(page: Long): MovieResponse
}