package com.privin.movies.model

import com.privin.movies.BuildConfig

data class Movie(
    var id: Long,
    var title: String,
    var backDropPath: String?,
    var posterPath: String?,
    var genres: List<String>?,
    var voteAverage: Double,
    var voteCount: Long,
    var releaseDate: String?
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