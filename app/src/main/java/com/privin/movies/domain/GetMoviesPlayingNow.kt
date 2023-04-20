package com.privin.movies.domain

import com.privin.movies.data.Repo
import com.privin.movies.model.Movie
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class GetMoviesPlayingNow @Inject constructor(
    private val repo: Repo,
    private val getAllGenres: dagger.Lazy<GetAllGenres>,
) {

    suspend fun execute(page: Long = 1): Pair<List<Movie>, Long> {
        val genres = try{
            getAllGenres.get().execute()
        }catch (e:Exception){
            emptyMap()
        }
        val response = repo.getMoviesNowPlaying(page)
        val list = response.results.map {
            it.toMovie(genres)
        }
        return Pair(list, response.page)
    }
}