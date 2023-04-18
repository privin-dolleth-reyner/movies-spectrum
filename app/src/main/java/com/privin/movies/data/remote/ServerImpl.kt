package com.privin.movies.data.remote

import com.privin.movies.data.model.MovieResponse
import javax.inject.Inject

class ServerImpl @Inject constructor(private val apiClient: ApiClient): Server {

    override suspend fun getNowPlayingMovies(): MovieResponse {
        return apiClient.getMoviesNowPlaying()
    }
}