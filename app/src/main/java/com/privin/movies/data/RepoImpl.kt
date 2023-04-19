package com.privin.movies.data

import com.privin.movies.data.model.MovieResponse
import com.privin.movies.data.remote.Server
import javax.inject.Inject

class RepoImpl @Inject constructor(private var server: Server): Repo {
    override suspend fun getMoviesNowPlaying(page: Long): MovieResponse {
        return server.getNowPlayingMovies(page)
    }

    override suspend fun getPopularMovies(page: Long): MovieResponse {
        return server.getPopularMovies(page)
    }
}