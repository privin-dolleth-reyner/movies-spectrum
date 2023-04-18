package com.privin.movies.data

import com.privin.movies.data.model.MovieResponse

interface Repo {

    suspend fun getMoviesNowPlaying(): MovieResponse
}