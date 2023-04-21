package com.privin.movies.data

import com.privin.movies.data.local.FavMovieEntity
import com.privin.movies.data.model.Genre
import com.privin.movies.data.model.MovieDetailResponse
import com.privin.movies.data.model.MovieResponse

interface Repo {

    suspend fun getMoviesNowPlaying(page: Long): MovieResponse
    suspend fun getPopularMovies(page: Long): MovieResponse
    suspend fun getUpcomingMovies(page: Long): MovieResponse
    suspend fun getTopRatedMovies(page: Long): MovieResponse
    suspend fun getGenre(): List<Genre>
    suspend fun getMovieDetail(id: Long): MovieDetailResponse
    suspend fun searchMovies(searchQuery: String, page: Long): MovieResponse
    suspend fun getFavMovies(): List<FavMovieEntity>
    suspend fun addToFav(favMovieEntity: FavMovieEntity)
    suspend fun removeFromFav(id: Long)
    suspend fun isFavMovie(id: Long): Boolean
}