package com.privin.movies.domain

import com.privin.movies.data.Repo
import com.privin.movies.model.Movie
import javax.inject.Inject

class GetFavouriteMovies @Inject constructor(
    private val repo: Repo
) {

    suspend fun execute(): List<Movie>{
        return repo.getFavMovies().map {
            Movie(
                id = it.id,
                title = it.title,
                backDropPath = it.backDropPath,
                posterPath = it.posterPath,
                voteCount = it.voteCount,
                genres = it.genres?.split(','),
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage
            )
        }
    }
}