package com.privin.movies.domain

import com.privin.movies.data.Repo
import com.privin.movies.data.local.FavMovieEntity
import com.privin.movies.model.MovieDetail
import javax.inject.Inject

class AddToFavMovie @Inject constructor(
    private val repo: Repo
) {

    suspend fun execute(movieDetail: MovieDetail) {
        val favMovieEntity = FavMovieEntity(
            id = movieDetail.id,
            title = movieDetail.title,
            backDropPath = movieDetail.backDropPath,
            posterPath = movieDetail.posterPath,
            genres = movieDetail.genres.joinToString(", ", transform = { it.name }),
            releaseDate = movieDetail.releaseDate,
            voteAverage = movieDetail.voteAverage,
            voteCount = movieDetail.voteCount
        )
        repo.addToFav(favMovieEntity)
    }
}