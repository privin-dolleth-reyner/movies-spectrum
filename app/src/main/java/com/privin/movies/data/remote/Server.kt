package com.privin.movies.data.remote

import com.privin.movies.data.model.GenreResponse
import com.privin.movies.data.model.MovieDetailResponse
import com.privin.movies.data.model.MovieResponse

interface Server {
    suspend fun getNowPlayingMovies(page: Long): MovieResponse
    suspend fun getPopularMovies(page: Long): MovieResponse
    suspend fun getUpcomingMovies(page: Long): MovieResponse
    suspend fun getTopRatedMovies(page: Long): MovieResponse
    suspend fun getGenres(): GenreResponse
    suspend fun getMovieDetail(id: Long): MovieDetailResponse
}