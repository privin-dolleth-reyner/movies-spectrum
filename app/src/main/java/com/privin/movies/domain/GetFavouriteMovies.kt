package com.privin.movies.domain

import com.privin.movies.data.Repo
import com.privin.movies.data.local.FavMovieEntity
import com.privin.movies.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetFavouriteMovies @Inject constructor(
    private val repo: Repo
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun execute(): Flow<List<Movie>> {
        return repo.getFavMovies().flatMapLatest {
            flowOf(mapToMovie(it))
        }
    }

    private fun mapToMovie(favMovieEntity: List<FavMovieEntity>): List<Movie> = favMovieEntity.map {
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