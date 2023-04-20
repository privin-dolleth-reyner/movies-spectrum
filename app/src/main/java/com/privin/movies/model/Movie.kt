package com.privin.movies.model

import com.privin.movies.BuildConfig

data class Movie(
    var id: Long,
    var title: String,
    var backDropPath: String?,
    var genres: List<String>?
){
    fun getBackDropUrl(): String? {
        if(backDropPath.isNullOrEmpty()) return null
        return "${BuildConfig.IMAGE_BASE_URL}$backDropPath"
    }
}