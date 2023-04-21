package com.privin.movies.data.model

import com.privin.movies.model.MovieDetail
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "backdrop_path")
    val backDropPath: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "genres")
    val genres: List<Genre>,
    @Json(name = "budget")
    val budget: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "tagline")
    val tagline: String?,
    @Json(name = "status")
    val status: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Long,
){
    fun toMovieDetail(): MovieDetail{
        return MovieDetail(
            id = id,
            title = title,
            overview = overview,
            backDropPath = backDropPath,
            posterPath = posterPath,
            genres = genres,
            budget = budget,
            releaseDate = releaseDate,
            tagline = tagline,
            status = status,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}

