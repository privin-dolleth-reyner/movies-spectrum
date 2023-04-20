package com.privin.movies.data.remote

import com.privin.movies.data.model.GenreResponse
import com.privin.movies.data.model.MovieResponse
import javax.inject.Inject

class ServerImpl @Inject constructor(private val apiClient: ApiClient): Server {

    override suspend fun getNowPlayingMovies(page: Long): MovieResponse {
        return apiClient.getMoviesNowPlaying(page)
    }

    override suspend fun getPopularMovies(page: Long): MovieResponse {
        return apiClient.getPopularMovies(page)
    }

    override suspend fun getUpcomingMovies(page: Long): MovieResponse {
        return apiClient.getUpcomingMovies(page)
    }

    override suspend fun getTopRatedMovies(page: Long): MovieResponse {
        return apiClient.getTopRatedMovies(page)
    }

    override suspend fun getGenres(): GenreResponse {
        return apiClient.getGenre()
    }
}