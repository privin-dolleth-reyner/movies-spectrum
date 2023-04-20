package com.privin.movies.domain

import com.privin.movies.data.Repo
import com.privin.movies.model.Movie
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(
    private val repo: Repo
) {

    suspend fun execute(page: Long = 1): Pair<List<Movie>, Long> {
        val response = repo.getTopRatedMovies(page)
        val list = response.results.map {
            it.toMovie()
        }
        return Pair(list, response.page)
    }
}