package com.privin.movies.data.remote

import com.privin.movies.data.model.MovieResponse
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiClient {

    @POST("movie/now_playing")
    suspend fun getMoviesNowPlaying(@Query("page") page: Long): MovieResponse
    @POST("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Long): MovieResponse
    @POST("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Long): MovieResponse

}