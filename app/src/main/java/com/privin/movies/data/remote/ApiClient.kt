package com.privin.movies.data.remote

import com.privin.movies.data.model.MovieResponse
import retrofit2.http.POST


interface ApiClient {

    @POST("movie/now_playing")
    suspend fun getMoviesNowPlaying(): MovieResponse

}