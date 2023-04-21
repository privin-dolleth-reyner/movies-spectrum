package com.privin.movies.domain

import com.privin.movies.data.Repo
import com.privin.movies.model.Movie
import javax.inject.Inject

class SearchMovies @Inject constructor(
    private val repo: Repo,
    private val getAllGenres: dagger.Lazy<GetAllGenres>,
) {

    suspend fun execute(searchQuery: String, page: Long = 1): Pair<List<Movie>, Long> {
        val genres = try{
            getAllGenres.get().execute()
        }catch (e:Exception){
            emptyMap()
        }
        val response = repo.searchMovies(searchQuery, page)
        val list = response.results.map {
            it.toMovie(genres)
        }
        return Pair(list, response.page)
    }
}