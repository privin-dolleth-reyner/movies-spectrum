package com.privin.movies.model

import com.privin.movies.BuildConfig
import com.privin.movies.data.model.Genre

data class MovieDetail(
    val id: Long,
    val title: String,
    val overview: String,
    val backDropPath: String?,
    val posterPath: String?,
    val genres: List<Genre>,
    val budget: String?,
    val releaseDate: String?,
    val tagline: String?,
    val status: String,
    val voteAverage: Double,
    val voteCount: Long,
){

    fun getBackDropUrl(): String? {
        if(backDropPath.isNullOrEmpty()) return null
        return "${BuildConfig.IMAGE_BASE_URL}$backDropPath"
    }


    fun getPosterUrl(): String? {
        if(posterPath.isNullOrEmpty()) return null
        return "${BuildConfig.IMAGE_BASE_URL}$posterPath"
    }
}