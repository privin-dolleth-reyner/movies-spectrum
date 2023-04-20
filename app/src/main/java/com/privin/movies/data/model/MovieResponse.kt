package com.privin.movies.data.model

import com.privin.movies.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "dates")
    var dates : Dates?,
    @Json(name = "page")
    var page : Long,
    @Json(name = "results")
    var results : List<MovieItem>,
    @Json(name = "total_pages")
    var totalPages : Long,
    @Json(name = "total_results")
    var totalResults : Long,
)
@JsonClass(generateAdapter = true)
data class Dates(
    @Json(name = "maximum")
    var maximum : String,
    @Json(name = "minimum")
    var minimum : String,
)
@JsonClass(generateAdapter = true)
data class MovieItem(
    @Json(name = "id")
    var id : Long,
    @Json(name = "original_language")
    var originalLanguage: String,
    @Json(name = "original_title")
    var originalTitle: String,
    @Json(name = "overview")
    var overview: String,
    @Json(name = "popularity")
    var popularity: Double,
    @Json(name = "poster_path")
    var posterPath: String?,
    @Json(name = "release_date")
    var releaseDate: String?,
    @Json(name = "title")
    var title: String,
    @Json(name = "video")
    var video: Boolean,
    @Json(name = "vote_average")
    var voteAverage: Double,
    @Json(name = "vote_count")
    var voteCount: Long,
    @Json(name = "adult")
    var adult: Boolean,
    @Json(name = "backdrop_path")
    var backdropPath: String?,
    @Json(name = "genre_ids")
    var genreIds: List<Int>
){
    fun toMovie(): Movie{
        return Movie(
            id,
            title,
            backdropPath
        )
    }
}