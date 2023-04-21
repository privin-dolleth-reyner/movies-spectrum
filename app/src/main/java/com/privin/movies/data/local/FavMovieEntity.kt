package com.privin.movies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavMovieEntity(
    @PrimaryKey
    var id: Long,
    var title: String,
    var backDropPath: String?,
    var posterPath: String?,
    var genres: String?,
    var voteAverage: Double,
    var voteCount: Long,
    var releaseDate: String?
)