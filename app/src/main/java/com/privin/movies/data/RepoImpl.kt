package com.privin.movies.data

import android.util.Log
import com.privin.movies.data.local.GenreDao
import com.privin.movies.data.local.GenreEntity
import com.privin.movies.data.model.Genre
import com.privin.movies.data.model.MovieDetailResponse
import com.privin.movies.data.model.MovieResponse
import com.privin.movies.data.remote.Server
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private var server: Server,
    private var genreDao: GenreDao
) : Repo {
    companion object {
        private const val TAG = "Repo"
    }

    override suspend fun getMoviesNowPlaying(page: Long): MovieResponse {
        return server.getNowPlayingMovies(page)
    }

    override suspend fun getPopularMovies(page: Long): MovieResponse {
        return server.getPopularMovies(page)
    }

    override suspend fun getUpcomingMovies(page: Long): MovieResponse {
        return server.getUpcomingMovies(page)
    }

    override suspend fun getTopRatedMovies(page: Long): MovieResponse {
        return server.getTopRatedMovies(page)
    }

    override suspend fun getGenre(): List<Genre> {
        val localList = genreDao.getAll().map {
            Genre(it.id, it.name)
        }
        if (localList.isNotEmpty())
            return localList
        return server.getGenres().genres.also { list ->
            updateGenreDb(list)
        }
    }

    override suspend fun getMovieDetail(id: Long): MovieDetailResponse {
        return server.getMovieDetail(id)
    }

    private suspend fun updateGenreDb(list: List<Genre>) {
        coroutineScope {
            launch {
                try {
                    val genreList = list.map {
                        GenreEntity(it.id, it.name)
                    }
                    genreDao.insertAll(genreList)
                } catch (e: Exception) {
                    Log.e(TAG, "updateGenre db: ", e)
                }
            }
        }
    }
}