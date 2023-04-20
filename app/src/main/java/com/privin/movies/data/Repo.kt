package com.privin.movies.data

import com.privin.movies.data.model.Genre
import com.privin.movies.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface Repo {

    suspend fun getMoviesNowPlaying(page: Long): MovieResponse
    suspend fun getPopularMovies(page: Long): MovieResponse
    suspend fun getUpcomingMovies(page: Long): MovieResponse
    suspend fun getTopRatedMovies(page: Long): MovieResponse
    suspend fun getGenre(): List<Genre>
}