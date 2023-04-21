package com.privin.movies.domain

import com.privin.movies.data.Repo
import com.privin.movies.model.Movie
import javax.inject.Inject

class GetTopRatedMovies @Inject constructor(
    private val repo: Repo,
    private val getAllGenres: dagger.Lazy<GetAllGenres>,
) {

    suspend fun execute(page: Long = 1): Pair<List<Movie>, Long> {
        val genres = getAllGenres.get().execute()
        val response = repo.getTopRatedMovies(page)
        val list = response.results.map {
            it.toMovie(genres)
        }
        return Pair(list, response.page)
    }
}