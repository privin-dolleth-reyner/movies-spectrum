package com.privin.movies.data.remote

import com.privin.movies.data.model.GenreResponse
import com.privin.movies.data.model.MovieDetailResponse
import com.privin.movies.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiClient {

    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying(@Query("page") page: Long): MovieResponse
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Long): MovieResponse
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Long): MovieResponse
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Long): MovieResponse
    @GET("genre/movie/list")
    suspend fun getGenre(): GenreResponse
    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Long): MovieDetailResponse
    @GET("search/movie")
    suspend fun searchMovies(@Query("query") searchQuery: String, @Query("page") page: Long): MovieResponse

}