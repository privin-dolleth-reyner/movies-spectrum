package com.privin.movies.domain

import com.privin.movies.data.Repo
import com.privin.movies.model.Movie
import javax.inject.Inject

class GetMoviesPlayingNow @Inject constructor(
    private val repo: Repo
) {

    suspend fun execute(): List<Movie> {
        return repo.getMoviesNowPlaying()
            .results.map {
                it.toMovie()
            }
    }
}